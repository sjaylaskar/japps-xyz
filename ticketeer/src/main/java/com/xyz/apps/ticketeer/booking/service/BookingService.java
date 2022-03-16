/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.booking.api.external.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDetailedInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatForShowResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatForShowResponseDtoList;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatInformationResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatInformationResponseDtoList;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatNumberPriceDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatPricesRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatPricesResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsCancellationRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsReservationRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsReservationResponseDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingConfirmationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDetailsDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDetailsDtoList;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationResponseDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.CancelBookingDto;
import com.xyz.apps.ticketeer.booking.model.Booking;
import com.xyz.apps.ticketeer.booking.model.BookingDetails;
import com.xyz.apps.ticketeer.booking.model.BookingDetailsRepository;
import com.xyz.apps.ticketeer.booking.model.BookingRepository;
import com.xyz.apps.ticketeer.booking.model.BookingStatus;
import com.xyz.apps.ticketeer.booking.model.Payment;
import com.xyz.apps.ticketeer.booking.model.PaymentRepository;
import com.xyz.apps.ticketeer.booking.model.PaymentStatus;
import com.xyz.apps.ticketeer.booking.service.modelmapper.BookingModelMapper;
import com.xyz.apps.ticketeer.general.resources.EnvironmentProperties;
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

    /** The booking model mapper. */
    @Autowired
    private BookingModelMapper bookingModelMapper;

    /**
     * The booking result.
     */
    private static final class BookingExternalReservationRequestResult {

        /** The booking reservation id. */
        private String bookingReservationId;

        /** The value. */
        private boolean value;
    }

    /**
     * Reserve.
     *
     * @param bookingReservationRequestDto the booking reservation request dto
     * @return the booking reservation response dto
     */
    public BookingReservationResponseDto reserve(@NotNull(
        message = "The booking reservation request cannot be null"
    ) final BookingReservationRequestDto bookingReservationRequestDto) {

        validateBooking(bookingReservationRequestDto);
        final BookingExternalReservationRequestResult bookingExternalReservationRequestResult =
            new BookingExternalReservationRequestResult();
        try {
            return reserve(bookingReservationRequestDto, bookingExternalReservationRequestResult);
        } catch (final Exception exception) {
            if (bookingExternalReservationRequestResult.value
                && StringUtils.isNotBlank(bookingExternalReservationRequestResult.bookingReservationId)) {
                bookingExternalApiHandlerService.cancel(
                    EventShowSeatsCancellationRequestDto.of(
                        bookingReservationRequestDto.getEventShowId(),
                        bookingReservationRequestDto.getSeatNumbers(),
                        bookingExternalReservationRequestResult.bookingReservationId));
            }
            throw exception;
        }
    }

    /**
     * Reserve.
     *
     * @param bookingReservationRequestDto the booking dto
     * @param bookingExternalReservationRequestResult the booking result
     * @return the booking dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    private BookingReservationResponseDto reserve(
            final BookingReservationRequestDto bookingReservationRequestDto,
            final BookingExternalReservationRequestResult bookingExternalReservationRequestResult) {

        final EventShowSeatsReservationResponseDto eventShowSeatsReservationResponse = bookingExternalApiHandlerService.reserve(
            EventShowSeatsReservationRequestDto.of(bookingReservationRequestDto.getEventShowId(), bookingReservationRequestDto
                .getSeatNumbers()));

        bookingExternalReservationRequestResult.value = true;
        bookingExternalReservationRequestResult.bookingReservationId = eventShowSeatsReservationResponse.getBookingReservationId();

        final EventShowSeatPricesResponseDto eventShowSeatPricesResponseDto = bookingExternalApiHandlerService
            .obtainEventShowSeatPrices(EventShowSeatPricesRequestDto.of(bookingReservationRequestDto.getEventShowId(),
                bookingReservationRequestDto.getSeatNumbers()));

        final EventShowDto eventShow = bookingExternalApiHandlerService.findEventShow(bookingReservationRequestDto
            .getEventShowId());

        final Booking booking = bookingRepository.save(
            bookingModelMapper.toReservedBooking(
                bookingReservationRequestDto,
                bookingExternalReservationRequestResult.bookingReservationId,
                calculateBaseAmountOfSeats(eventShowSeatPricesResponseDto),
                eventShow.getCityId()));

        return bookingModelMapper.of(booking, bookingReservationRequestDto.getSeatNumbers());

    }

    /**
     * Confirm.
     *
     * @param bookingConfirmationRequestDto the booking dto
     * @return the booking dto
     */
    public BookingDetailsDto confirm(@NotNull(
        message = "The booking confirmation request cannot be null"
    ) final BookingConfirmationRequestDto bookingConfirmationRequestDto) {

        validateBooking(bookingConfirmationRequestDto);
        final BookingExternalReservationRequestResult bookingExternalReservationRequestResult = new BookingExternalReservationRequestResult();
        try {
            return confirm(bookingConfirmationRequestDto, bookingExternalReservationRequestResult);
        } catch (final Exception exception) {
            if (bookingExternalReservationRequestResult.value
                    && StringUtils.isNotBlank(bookingExternalReservationRequestResult.bookingReservationId)) {
                    bookingExternalApiHandlerService.cancel(
                        EventShowSeatsCancellationRequestDto.of(
                            bookingConfirmationRequestDto.getEventShowId(),
                            bookingConfirmationRequestDto.getSeatNumbers(),
                            bookingExternalReservationRequestResult.bookingReservationId));
                }
                throw exception;
        }
    }

    /**
     * Confirm.
     *
     * @param bookingConfirmationRequestDto the booking dto
     * @param bookingResult the booking result
     * @return the booking dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    private BookingDetailsDto confirm(
            final BookingConfirmationRequestDto bookingConfirmationRequestDto,
            final BookingExternalReservationRequestResult bookingResult) {

        final BookingDetails bookingDetails = prepareBookingDetails(bookingConfirmationRequestDto);

        final Booking booking = bookingRepository.findById(bookingConfirmationRequestDto.getBookingId()).orElseThrow(BookingExpiredException::new);

        final EventShowSeatsBookingDto eventShowSeatsBookingDto = new EventShowSeatsBookingDto();
        eventShowSeatsBookingDto.setBookingId(booking.getId());
        eventShowSeatsBookingDto.setSeatIds(bookingConfirmationRequestDto.getEventShowSeatIds());

        if (!bookingExternalApiHandlerService.areEventShowSeatsReserved(eventShowSeatsBookingDto)) {
            throw new SelectedSeatsUnavailableException();
        }

        final double baseAmount = bookingExternalApiHandlerService.obtainEventShowSeatPrices(bookingConfirmationRequestDto);

        bookingConfirmationRequestDto.setAmount(baseAmount);
        bookingConfirmationRequestDto.setFinalAmount(baseAmount);

        final BookingPriceInfoDto bookingPriceInfoDto = toBookingPriceInfo(bookingConfirmationRequestDto);

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

        final int countOfBookedSeats = bookingExternalApiHandlerService.book(eventShowSeatsBookingDto);

        if (countOfBookedSeats != bookingConfirmationRequestDto.getEventShowSeatIds().size()) {
            bookingExternalApiHandlerService.cancel(booking.getId());
            throw new SelectedSeatsUnavailableException();
        }
        bookingResult.value = true;
        bookingResult.bookingId = bookingUpdated.getId();
        return toBookingDetailsDto(bookingUpdated, bookingDetailsSaved);
    }

    /**
     * Cancel.
     *
     * @param cancelBookingDto the cancel booking dto
     * @return the booking dto
     */
    public boolean cancel(@NotNull(message = "The booking cannot be null") final CancelBookingDto cancelBookingDto) {

        final BookingExternalReservationRequestResult bookingReservationResult = new BookingExternalReservationRequestResult();
        cancel(cancelBookingDto, bookingReservationResult);
        bookingExternalApiHandlerService.cancel(bookingReservationResult.bookingId);
        return true;
    }

    /**
     * Cancel.
     *
     * @param cancelBookingDto the cancel booking dto
     * @param bookingResult the booking result
     * @return true, if successful
     */
    @Transactional(rollbackFor = {Throwable.class})
    private void cancel(final CancelBookingDto cancelBookingDto, final BookingExternalReservationRequestResult bookingResult) {

        bookingExternalApiHandlerService.validateUser(cancelBookingDto.getUsername(), cancelBookingDto.getPassword());
        final Booking bookingByUsernameAndId = bookingRepository.findByUsernameAndId(cancelBookingDto.getUsername(),
            cancelBookingDto
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

        return mongoTemplate().findOne(new Query().addCriteria(Criteria.where("bookingId").is(bookingId)).limit(1),
            BookingDetails.class);
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
    private BookingDetails prepareBookingDetails(final BookingDto bookingDto) {

        final BookingDetails bookingDetails = new BookingDetails();
        final EventShowDetailedInfoDto eventShowDetailedInfoDto = bookingExternalApiHandlerService
            .findEventShowDetailsByEventShowId(
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
     * Calculate base amount of seats.
     *
     * @param eventShowSeatPricesResponseDto the event show seat prices response dto
     * @return the base amount
     */
    private Double calculateBaseAmountOfSeats(final EventShowSeatPricesResponseDto eventShowSeatPricesResponseDto) {

        return eventShowSeatPricesResponseDto.getEventShowSeatNumberPrices().stream().map(EventShowSeatNumberPriceDto::getAmount)
            .mapToDouble(Double::valueOf).sum();
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
     * @param bookingConfirmationRequestDto the booking confirmation request dto
     */
    private void validateBooking(final BookingConfirmationRequestDto bookingConfirmationRequestDto) {

        validateUser(bookingConfirmationRequestDto.getUsername(), bookingConfirmationRequestDto.getPassword());

        final Optional<Booking> optionalBooking = bookingRepository
            .findFirstByIdAndBookingReservationIdAndEventShowIdAndUsernameOrderByReservationTimeAsc(
                bookingConfirmationRequestDto.getBookingId(), UUID.fromString(bookingConfirmationRequestDto
                    .getBookingReservationId()),
                bookingConfirmationRequestDto.getEventShowId(), bookingConfirmationRequestDto.getUsername());

        if (optionalBooking.isEmpty()) {
            throw new BookingServiceException("Invalid booking request.");
        }

        final EventShowSeatInformationResponseDtoList eventShowSeatsForReservation = bookingExternalApiHandlerService
            .findEventShowSeatsByBookingReservation(bookingConfirmationRequestDto.getEventShowId(), optionalBooking.get()
                .getBookingReservationId().toString());

        if (eventShowSeatsForReservation.getDtos().size() != bookingConfirmationRequestDto.getSeatNumbers().size()
            || bookingConfirmationRequestDto.getSeatNumbers()
            .stream().anyMatch(seatNumber -> !eventShowSeatsForReservation.getDtos().stream().map(
                EventShowSeatInformationResponseDto::getSeatNumber).collect(Collectors.toSet()).contains(seatNumber))) {
            throw new BookingServiceException("Invalid seats for reservation.");
        }
    }

    /**
     * Validate booking.
     *
     * @param bookingReservationRequestDto the booking dto
     */
    private void validateBooking(final BookingReservationRequestDto bookingReservationRequestDto) {

        validateUser(bookingReservationRequestDto.getUsername(), bookingReservationRequestDto.getPassword());

        validateEventShow(bookingReservationRequestDto.getEventShowId());

        validateEventShowSeats(bookingReservationRequestDto.getEventShowId(), bookingReservationRequestDto.getSeatNumbers());
    }

    /**
     * Validate user.
     *
     * @param username the username
     * @param password the password
     */
    private void validateUser(final String username, final String password) {

        bookingExternalApiHandlerService.validateUser(username, password);
    }

    /**
     * Validate event show.
     *
     * @param eventShowId the event show id
     */
    private void validateEventShow(final Long eventShowId) {

        if (bookingExternalApiHandlerService.findEventShow(eventShowId) == null) {
            throw new BookingServiceException("Invalid event show id: " + eventShowId);
        }
    }

    /**
     * Validate event show seats.
     *
     * @param eventShowId the event show id
     * @param bookingRequestSeatNumbers the booking request seat numbers
     */
    private void validateEventShowSeats(final Long eventShowId,
            final Set<String> bookingRequestSeatNumbers) {

        if (CollectionUtils.isEmpty(bookingRequestSeatNumbers)) {
            throw new BookingServiceException("At least select one seat to reserve.");
        }

        final int maxSeatsPerBooking = EnvironmentProperties.get(environment()).maxSeatsPerBooking();
        if (bookingRequestSeatNumbers.size() > maxSeatsPerBooking) {
            throw new BookingServiceException("Maximum seats allowed per booking is: " + maxSeatsPerBooking);
        }

        final EventShowSeatForShowResponseDtoList eventShowSeatListForShow = bookingExternalApiHandlerService
            .findEventShowSeatsForShow(eventShowId);

        final Map<String, List<EventShowSeatForShowResponseDto>> eventShowSeatsBySeatNumberMap = eventShowSeatListForShow.getDtos()
            .stream().collect(Collectors.groupingBy(EventShowSeatForShowResponseDto::getSeatNumber));

        if (isInvalidSeatsSelection(bookingRequestSeatNumbers, eventShowSeatsBySeatNumberMap)) {
            throw new BookingServiceException("Invalid seats selected.");
        }

        if (isAnySeatUnavailableForReservation(bookingRequestSeatNumbers, eventShowSeatsBySeatNumberMap)) {
            throw new SelectedSeatsUnavailableException();
        }
    }

    /**
     * Checks if is invalid seats selection.
     *
     * @param bookingRequestSeatNumbers the booking request seat numbers
     * @param eventShowSeatsBySeatNumberMap the event show seats by seat number map
     * @return true, if is invalid seats selection
     */
    private boolean isInvalidSeatsSelection(final Set<String> bookingRequestSeatNumbers,
            final Map<String, List<EventShowSeatForShowResponseDto>> eventShowSeatsBySeatNumberMap) {

        return bookingRequestSeatNumbers.stream().anyMatch(seatNumber -> !eventShowSeatsBySeatNumberMap
            .containsKey(seatNumber));
    }

    /**
     * Checks if is any seat unavailable for reservation.
     *
     * @param bookingRequestSeatNumbers the booking request seat numbers
     * @param eventShowSeatsBySeatNumberMap the event show seats by seat number map
     * @return true, if is any seat unavailable for reservation
     */
    private boolean isAnySeatUnavailableForReservation(final Set<String> bookingRequestSeatNumbers,
            final Map<String, List<EventShowSeatForShowResponseDto>> eventShowSeatsBySeatNumberMap) {

        return eventShowSeatsBySeatNumberMap.entrySet().stream().filter(eventShowSeatEntry -> bookingRequestSeatNumbers.contains(
            eventShowSeatEntry.getKey()))
            .anyMatch(eventShowSeatEntry -> !StringUtils.equalsIgnoreCase(eventShowSeatEntry.getValue().get(0)
                .getSeatReservationStatus(), "AVAILABLE"));
    }
}
