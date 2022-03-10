/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.booking.api.external.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDetailedInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDetailsDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDetailsDtoList;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.CancelBookingDto;
import com.xyz.apps.ticketeer.booking.model.Booking;
import com.xyz.apps.ticketeer.booking.model.BookingDetails;
import com.xyz.apps.ticketeer.booking.model.BookingDetailsRepository;
import com.xyz.apps.ticketeer.booking.model.BookingRepository;
import com.xyz.apps.ticketeer.booking.model.BookingStatus;
import com.xyz.apps.ticketeer.booking.model.Payment;
import com.xyz.apps.ticketeer.booking.model.PaymentRepository;
import com.xyz.apps.ticketeer.booking.model.PaymentStatus;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;


/**
 * The booking service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class BookingService extends GeneralService {

    /** The max seats per booking. */
    private static final int MAX_SEATS_PER_BOOKING = 6;

    /** The booking repository. */
    @Autowired
    private BookingRepository bookingRepository;

    /** The payment repository. */
    @Autowired
    private PaymentRepository paymentRepository;

    /** The external api handler service. */
    @Autowired
    private BookingExternalApiHandlerService bookingExternalApiHandlerService;

    /** The booking details repository. */
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;

    /**
     * The booking result.
     */
    private static final class BookingResult {
        private Long bookingId;
        private boolean value;
    }

    /**
     * Reserve.
     *
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    public BookingDto reserve(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {
        validateBooking(bookingDto);
        final BookingResult bookingResult = new BookingResult();
        try {
            return reserve(bookingDto, bookingResult);
        } catch (final Exception exception) {
            if (bookingResult.value) {
                bookingExternalApiHandlerService.cancelBookingForEventShowSeats(bookingResult.bookingId);
            }
            throw exception;
        }
    }

    /**
     * Reserve.
     *
     * @param bookingDto the booking dto
     * @param bookingResult the booking result
     * @return the booking dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    private BookingDto reserve(final BookingDto bookingDto, final BookingResult bookingResult) {

        if (BooleanUtils.isTrue(bookingExternalApiHandlerService.areEventShowSeatsAvailable(bookingDto))) {

            Integer reservedBookingSeatCount = bookingExternalApiHandlerService.reserveSeats(bookingDto);
            if (reservedBookingSeatCount != bookingDto.getEventShowSeatIds().size()) {

                bookingExternalApiHandlerService.unreserveSeats(bookingDto);

                throw new SelectedSeatsUnavailableException();
            }

            final Booking booking = bookingRepository.save(toReservedBooking(bookingDto));

            final EventShowSeatsBookingDto eventShowSeatsBookingDto = new EventShowSeatsBookingDto();
            eventShowSeatsBookingDto.setBookingId(booking.getId());
            eventShowSeatsBookingDto.setSeatIds(bookingDto.getEventShowSeatIds());

            reservedBookingSeatCount = bookingExternalApiHandlerService.reserveSeatsWithBooking(eventShowSeatsBookingDto);
            if (reservedBookingSeatCount != booking.getNumberOfSeats()) {
                bookingExternalApiHandlerService.cancelBookingForEventShowSeats(booking.getId());
                throw new SelectedSeatsUnavailableException();
            }
            bookingResult.value = true;
            bookingResult.bookingId = booking.getId();
            return toReservedBookingDto(booking, bookingDto);
        }

        throw new SelectedSeatsUnavailableException();
    }

    /**
     * Confirm.
     *
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    public BookingDetailsDto confirm(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {
        if (bookingDto.getBookingId() == null) {
            throw new BookingExpiredException();
        }
        validateBooking(bookingDto);
        final BookingResult bookingReservationResult = new BookingResult();
        try {
            return confirm(bookingDto, bookingReservationResult);
        } catch (final Exception exception) {
            if (bookingReservationResult.value) {
                bookingExternalApiHandlerService.cancelBookingForEventShowSeats(bookingReservationResult.bookingId);

                bookingDetailsRepository.deleteByBookingId(bookingReservationResult.bookingId);
            }
            throw exception;
        }
    }

    /**
     * Confirm.
     *
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    private BookingDetailsDto confirm(final BookingDto bookingDto, final BookingResult bookingResult) {

        final BookingDetails bookingDetails = prepareBookingDetails(bookingDto);

        final Booking booking = bookingRepository.findById(bookingDto.getBookingId()).orElseThrow(BookingExpiredException::new);

        final EventShowSeatsBookingDto eventShowSeatsBookingDto = new EventShowSeatsBookingDto();
        eventShowSeatsBookingDto.setBookingId(booking.getId());
        eventShowSeatsBookingDto.setSeatIds(bookingDto.getEventShowSeatIds());

        if (!bookingExternalApiHandlerService.areEventShowSeatsReserved(eventShowSeatsBookingDto)) {
            throw new SelectedSeatsUnavailableException();
        }

        final double baseAmount = bookingExternalApiHandlerService.calculateEventShowSeatsAmount(bookingDto);

        bookingDto.setAmount(baseAmount);
        bookingDto.setFinalAmount(baseAmount);

        final BookingPriceInfoDto bookingPriceInfoDto = toBookingPriceInfo(bookingDto);

        final double finalAmount = bookingExternalApiHandlerService.calculateFinalBookingAmount(bookingPriceInfoDto);

        final Payment payment = new Payment();
        payment.setAmount(finalAmount);
        payment.setBooking(booking);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(Payment.DEFAULT_TRANSACTION_ID_PREFIX + "_" + booking.getPhoneNumber() + "_" + booking.getId());
        paymentRepository.save(payment);

        booking.setAmount(baseAmount);
        booking.setFinalAmount(finalAmount);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingTime(LocalDateTimeFormatUtil.parseLocalDateTime(bookingPriceInfoDto.getBookingTime()));

        final Booking bookingUpdated = bookingRepository.save(booking);
        bookingDetails.setBookingId(bookingUpdated.getId());
        final BookingDetails bookingDetailsSaved = bookingDetailsRepository.save(bookingDetails);

        final int countOfBookedSeats = bookingExternalApiHandlerService.bookSeats(eventShowSeatsBookingDto);

        if (countOfBookedSeats != bookingDto.getEventShowSeatIds().size()) {
            bookingExternalApiHandlerService.cancelBookingForEventShowSeats(booking.getId());
            throw new SelectedSeatsUnavailableException();
        }
        bookingResult.value = true;
        bookingResult.bookingId = bookingUpdated.getId();
        return toBookingDetailsDto(bookingUpdated, bookingDetailsSaved);
    }

    /**
     * Cancel.
     *
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    public boolean cancel(@NotNull(message = "The booking cannot be null") final CancelBookingDto cancelBookingDto) {
        final BookingResult bookingReservationResult = new BookingResult();
        cancel(cancelBookingDto, bookingReservationResult);
        bookingExternalApiHandlerService.cancelBookingForEventShowSeats(bookingReservationResult.bookingId);
        return true;
    }

    /**
     * Cancel.
     *
     * @param the booking dto
     * @return true, if successful
     */
    @Transactional(rollbackFor = {Throwable.class})
    private void cancel(final CancelBookingDto cancelBookingDto, final BookingResult bookingResult) {

        bookingExternalApiHandlerService.validateUser(cancelBookingDto.getUsername(), cancelBookingDto.getPassword());
        final Booking bookingByUsernameAndId = bookingRepository.findByUsernameAndId(cancelBookingDto.getUsername(), cancelBookingDto
            .getBookingId());
        if (bookingByUsernameAndId == null) {
            throw new BookingNotFoundException(cancelBookingDto.getBookingId());
        }
        if (!BookingStatus.CONFIRMED.equals(bookingByUsernameAndId.getBookingStatus())) {
            throw new BookingServiceException("No confirmed booking found for booking id: " + bookingByUsernameAndId.getId());
        }

        bookingByUsernameAndId.setBookingStatus(BookingStatus.CANCELLED);
        final Booking bookingCancelled = bookingRepository.save(bookingByUsernameAndId);
        final Payment payment = paymentRepository.findSuccessfulPaymentByBooking(bookingCancelled);
        if (payment == null) {
            throw new SuccessfulPaymentNotFoundForBookingException(bookingCancelled.getId());
        }
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        bookingResult.value = true;
        bookingResult.bookingId = bookingCancelled.getId();
    }

    /**
     * Finds the by username and id.
     *
     * @param username the username
     * @param id the id
     * @return the booking details dto
     */
    public BookingDetailsDto findByUsernameAndId(
            @NotBlank(message = "The username cannot be empty.") final String username,
            @NotNull(message = "The booking id cannot be null.") final Long id) {
        final Booking booking = bookingRepository.findByUsernameAndId(username, id);
        if (booking == null) {
            throw new BookingNotFoundException(id);
        }
        final BookingDetails bookingDetails = findBookingDetailsByBookingId(id);

        return toBookingDetailsDto(booking, bookingDetails);
    }

    /**
     * Finds the by username.
     *
     * @param username the username
     * @return the booking details dto list
     */
    public BookingDetailsDtoList findByUsername(@NotBlank(message = "The username cannot be empty.") final String username) {
        final List<Booking> bookings = bookingRepository.findByUsername(username);
        if (CollectionUtils.isEmpty(bookings)) {
            throw BookingNotFoundException.forUsername(username);
        }

        return new BookingDetailsDtoList(bookings.stream().map(booking -> toBookingDetailsDto(booking,
            findBookingDetailsByBookingId(booking.getId()))).toList());
    }

    /**
     * Finds the booking details by booking id.
     *
     * @param bookingId the booking id
     * @return the booking details
     */
    private BookingDetails findBookingDetailsByBookingId(final Long bookingId) {

        return serviceBeansFetcher().mongoTemplate().findOne(new Query().addCriteria(Criteria.where("bookingId").is(bookingId)).limit(1), BookingDetails.class);
    }

    /**
     * To booking details dto.
     *
     * @param booking the booking
     * @param bookingDetails the booking details
     * @return the booking details dto
     */
    private BookingDetailsDto toBookingDetailsDto(final Booking booking, final BookingDetails bookingDetails) {

        final BookingDetailsDto bookingDetailsDto = new BookingDetailsDto();
        bookingDetailsDto.setBookingId(booking.getId());
        bookingDetailsDto.setBookingStatus(booking.getBookingStatus().name());
        bookingDetailsDto.setBookingTime(LocalDateTimeFormatUtil.format(booking.getBookingTime()));
        bookingDetailsDto.setUsername(booking.getUsername());
        bookingDetailsDto.setEmailId(booking.getEmailId());
        bookingDetailsDto.setPhoneNumber(booking.getPhoneNumber());
        bookingDetailsDto.setOfferCode(booking.getOfferCode());
        bookingDetailsDto.setBaseAmount(booking.getAmount());
        bookingDetailsDto.setFinalAmount(booking.getFinalAmount());
        bookingDetailsDto.setIsConfirmed(BookingStatus.CONFIRMED.equals(booking.getBookingStatus()));
        fillBookingDetailsInfo(bookingDetails, bookingDetailsDto);
        return bookingDetailsDto;
    }

    /**
     * Fill booking details info.
     *
     * @param bookingDetails the booking details
     * @param bookingDetailsDto the booking details dto
     */
    private void fillBookingDetailsInfo(final BookingDetails bookingDetails, final BookingDetailsDto bookingDetailsDto) {
        if (bookingDetails != null) {
            bookingDetailsDto.setEventName(bookingDetails.getEventName());
            bookingDetailsDto.setCityName(bookingDetails.getCityName());
            bookingDetailsDto.setEventVenueName(bookingDetails.getEventVenueName());
            bookingDetailsDto.setAuditoriumName(bookingDetails.getAuditoriumName());
            bookingDetailsDto.setEventShowDate(LocalDateTimeFormatUtil.format(bookingDetails.getEventShowDate()));
            bookingDetailsDto.setEventShowTime(LocalDateTimeFormatUtil.format(bookingDetails.getEventShowTime()));
            bookingDetailsDto.setSeatNumbers(bookingDetails.getSeatNumbers());
        }
    }

    /**
     * Save booking details.
     *
     * @param bookingDto the booking dto
     * @return the booking details
     */
    private BookingDetails prepareBookingDetails(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        final BookingDetails bookingDetails = new BookingDetails();
        final EventShowDetailedInfoDto eventShowDetailedInfoDto = bookingExternalApiHandlerService.findEventShowDetailsByEventShowId(
            bookingDto.getEventShowId());
        bookingDetails.setEventShowDate(LocalDateTimeFormatUtil.parseLocalDate(eventShowDetailedInfoDto.getDate()));
        bookingDetails.setEventShowTime(LocalDateTimeFormatUtil.parseLocalTime(eventShowDetailedInfoDto.getStartTime()));
        bookingDetails.setCityName(eventShowDetailedInfoDto.getCityName());
        bookingDetails.setEventName(eventShowDetailedInfoDto.getEventName());
        bookingDetails.setEventVenueName(eventShowDetailedInfoDto.getEventVenueName());
        bookingDetails.setAuditoriumName(eventShowDetailedInfoDto.getAuditoriumName());
        bookingDetails.setSeatNumbers(bookingExternalApiHandlerService.findSeatNumbers(bookingDto.getEventShowSeatIds()));
        return bookingDetails;
    }

    /**
     * To reserved booking dto.
     *
     * @param booking the booking
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    private BookingDto toReservedBookingDto(final Booking booking, final BookingDto bookingDto) {

        bookingDto.setBookingId(booking.getId());
        bookingDto.setAmount(booking.getAmount());
        bookingDto.setFinalAmount(booking.getFinalAmount());
        bookingDto.setReservationTime(booking.getReservationTime().toString());
        bookingDto.setIsReserved(BookingStatus.RESERVED.equals(booking.getBookingStatus()));
        bookingDto.setBookingStatus(booking.getBookingStatus() != null ? booking.getBookingStatus().name() : null);
        return bookingDto;
    }

    /**
     * To reserved booking.
     *
     * @param bookingDto the booking dto
     * @return the booking
     */
    private Booking toReservedBooking(final BookingDto bookingDto) {

        final Double amount = bookingExternalApiHandlerService.calculateEventShowSeatsAmount(bookingDto);

        final Booking booking = new Booking();
        booking.setReservationTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.RESERVED);
        booking.setNumberOfSeats(bookingDto.getEventShowSeatIds().size());
        booking.setAmount(amount);
        booking.setFinalAmount(amount);
        booking.setOfferCode(bookingDto.getOfferCode());
        booking.setCityId(bookingDto.getCityId());
        booking.setEventShowId(bookingDto.getEventShowId());
        booking.setUsername(bookingDto.getUsername());
        booking.setPhoneNumber(bookingDto.getPhoneNumber());
        booking.setEmailId(bookingDto.getEmailId());

        return booking;
    }

    /**
     * To booking price info.
     *
     * @param bookingDto the booking dto
     * @return the booking price info
     */
    private BookingPriceInfoDto toBookingPriceInfo(final BookingDto bookingDto) {

        final BookingPriceInfoDto bookingPriceInfoDto = new BookingPriceInfoDto();
        bookingPriceInfoDto.setOfferCode(bookingDto.getOfferCode());
        bookingPriceInfoDto.setBaseAmount(bookingDto.getAmount());
        bookingPriceInfoDto.setFinalAmount(bookingDto.getFinalAmount());
        bookingPriceInfoDto.setNumberOfSeats(bookingDto.getEventShowSeatIds().size());
        bookingPriceInfoDto.setCityId(bookingDto.getCityId());
        bookingPriceInfoDto.setEventVenueId(bookingDto.getEventVenueId());
        final EventShowDto eventShow = bookingExternalApiHandlerService.findEventShow(bookingDto);
        bookingPriceInfoDto.setEventId(eventShow.getEventId());
        bookingPriceInfoDto.setEventShowId(bookingDto.getEventShowId());
        bookingPriceInfoDto.setShowDate(eventShow.getDate());
        bookingPriceInfoDto.setShowStartTime(eventShow.getStartTime());
        bookingPriceInfoDto.setBookingTime(LocalDateTimeFormatUtil.format(LocalDateTime.now()));
        return bookingPriceInfoDto;
    }

    /**
     * Validate booking.
     *
     * @param bookingDto the booking dto
     */
    private void validateBooking(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        validateUser(bookingDto);

        validateEventShow(bookingDto);

        validateCity(bookingDto);

        validateEventShowSeats(bookingDto);
    }

    /**
     * Validate city.
     *
     * @param bookingDto the booking dto
     */
    private void validateCity(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        if (bookingExternalApiHandlerService.findCity(bookingDto) == null) {
            throw new BookingServiceException("Invalid city id: " + bookingDto.getEventShowId());
        }
    }

    /**
     * Validate user.
     *
     * @param bookingDto the booking dto
     */
    private void validateUser(final BookingDto bookingDto) {

        bookingExternalApiHandlerService.validateUser(bookingDto.getUsername(), bookingDto.getPassword());
    }

    /**
     * Validate event show.
     *
     * @param bookingDto the booking dto
     */
    private void validateEventShow(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        if (bookingExternalApiHandlerService.findEventShow(bookingDto) == null) {
            throw new BookingServiceException("Invalid event show id: " + bookingDto.getEventShowId());
        }
    }

    /**
     * Validate event show seats.
     *
     * @param bookingDto the booking dto
     */
    private void validateEventShowSeats(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        if (CollectionUtils.isEmpty(bookingDto.getEventShowSeatIds())) {
            throw new BookingServiceException("At least select one seat to reserve.");
        }

        if (bookingDto.getEventShowSeatIds().size() > MAX_SEATS_PER_BOOKING) {
            throw new BookingServiceException("Maximum seats allowed per booking is: " + MAX_SEATS_PER_BOOKING);
        }
        final Set<Long> eventShowSeatIds = bookingExternalApiHandlerService.findEventShowSeatIdsByEventShowId(bookingDto.getEventShowId());

        if (!bookingDto.getEventShowSeatIds().stream().allMatch(eventShowSeatId -> eventShowSeatIds.contains(eventShowSeatId))) {
            throw new BookingServiceException("Invalid seats selected.");
        }
    }
}
