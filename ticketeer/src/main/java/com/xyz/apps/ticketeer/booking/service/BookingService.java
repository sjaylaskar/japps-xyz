/*
 * Id: BookingService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
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

import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDetailedInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatForShowResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatForShowResponseDtoList;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatInformationResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatInformationResponseDtoList;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatNumberPriceDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatPricesRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatPricesResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsCancellationRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsReservationRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsReservationResponseDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingCancellationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingConfirmationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDetailsDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDetailsDtoList;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingRequestValidator;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationResponseDto;
import com.xyz.apps.ticketeer.booking.model.Booking;
import com.xyz.apps.ticketeer.booking.model.BookingDetails;
import com.xyz.apps.ticketeer.booking.model.BookingDetailsRepository;
import com.xyz.apps.ticketeer.booking.model.BookingRepository;
import com.xyz.apps.ticketeer.booking.model.BookingStatus;
import com.xyz.apps.ticketeer.booking.model.Payment;
import com.xyz.apps.ticketeer.booking.model.PaymentRepository;
import com.xyz.apps.ticketeer.booking.model.PaymentStatus;
import com.xyz.apps.ticketeer.booking.resources.Messages;
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

    /** The seat reservation status available. */
    private static final String SEAT_RESERVATION_STATUS_AVAILABLE = "AVAILABLE";

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
        BookingRequestValidator.validate(bookingReservationRequestDto);
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

        final EventShowDto eventShow = bookingExternalApiHandlerService.findEventShow(bookingReservationRequestDto
            .getEventShowId());

        final EventShowSeatPricesResponseDto eventShowSeatPricesResponseDto = bookingExternalApiHandlerService
            .obtainEventShowSeatPrices(EventShowSeatPricesRequestDto.of(bookingReservationRequestDto.getEventShowId(),
                bookingReservationRequestDto.getSeatNumbers()));

        final EventShowSeatsReservationResponseDto eventShowSeatsReservationResponse = reserveEventShowSeats(
            bookingReservationRequestDto);

        bookingExternalReservationRequestResult.value = true;
        bookingExternalReservationRequestResult.bookingReservationId = eventShowSeatsReservationResponse.getBookingReservationId();

        final Booking booking = bookingRepository.save(
            bookingModelMapper.toReservedBooking(
                bookingReservationRequestDto,
                bookingExternalReservationRequestResult.bookingReservationId,
                calculateBaseAmountOfSeats(eventShowSeatPricesResponseDto),
                eventShow.getCityId()));

        return bookingModelMapper.of(booking, bookingReservationRequestDto.getSeatNumbers());

    }

    /**
     * Reserve event show seats.
     *
     * @param bookingReservationRequestDto the booking reservation request dto
     * @return the event show seats reservation response dto
     */
    private EventShowSeatsReservationResponseDto reserveEventShowSeats(
            final BookingReservationRequestDto bookingReservationRequestDto) {

        final EventShowSeatsReservationResponseDto eventShowSeatsReservationResponse = bookingExternalApiHandlerService.reserve(
            EventShowSeatsReservationRequestDto.of(bookingReservationRequestDto.getEventShowId(), bookingReservationRequestDto
                .getSeatNumbers()));

        if (eventShowSeatsReservationResponse == null
            || StringUtils.isBlank(eventShowSeatsReservationResponse.getBookingReservationId())) {
            throw new SelectedSeatsUnavailableException();
        }
        return eventShowSeatsReservationResponse;
    }

    /**
     * Confirm.
     *
     * @param bookingConfirmationRequestDto the booking dto
     * @return the booking dto
     */
    public BookingDetailsDto confirm(@NotNull(
        message = Messages.MESSAGE_ERROR_REQUIRED_BOOKING_CONFIRMATION_REQUEST
    ) final BookingConfirmationRequestDto bookingConfirmationRequestDto) {
        BookingRequestValidator.validate(bookingConfirmationRequestDto);
        validateBooking(bookingConfirmationRequestDto);
        final BookingExternalReservationRequestResult bookingExternalReservationRequestResult =
            new BookingExternalReservationRequestResult();
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
     * @param bookingExternalReservationRequestResult the booking external reservation request result
     * @return the booking dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    private BookingDetailsDto confirm(
            final BookingConfirmationRequestDto bookingConfirmationRequestDto,
            final BookingExternalReservationRequestResult bookingExternalReservationRequestResult) {

        final EventShowDto eventShow = bookingExternalApiHandlerService.findEventShow(bookingConfirmationRequestDto
            .getEventShowId());

        final EventShowSeatPricesResponseDto eventShowSeatPricesResponseDto = bookingExternalApiHandlerService
            .obtainEventShowSeatPrices(
                EventShowSeatPricesRequestDto.of(bookingConfirmationRequestDto.getEventShowId(),
                    bookingConfirmationRequestDto.getSeatNumbers()));

        final EventShowDetailedInfoDto eventShowDetailedInfoDto = bookingExternalApiHandlerService
            .findEventShowDetailsByEventShowId(
                bookingConfirmationRequestDto.getEventShowId());

        final Booking booking = bookingRepository.findById(bookingConfirmationRequestDto.getBookingId()).orElseThrow(
            BookingExpiredException::new);

        booking.setAmount(calculateBaseAmountOfSeats(eventShowSeatPricesResponseDto));
        final LocalDateTime bookingTime = LocalDateTime.now();
        booking.setBookingTime(bookingTime);

        final double finalAmount = bookingExternalApiHandlerService.calculateFinalBookingAmount(bookingModelMapper
            .toBookingPriceInfo(
                bookingConfirmationRequestDto, eventShow, LocalDateTimeFormatUtil.format(bookingTime),
                eventShowSeatPricesResponseDto));

        final BookingDetails bookingDetails = bookingModelMapper.toBookingDetails(bookingConfirmationRequestDto,
            eventShowSeatPricesResponseDto, eventShowDetailedInfoDto);

        final EventShowSeatsBookingResponseDto eventShowSeatsBookingResponse = confirmEventShowSeats(bookingConfirmationRequestDto);

        bookingExternalReservationRequestResult.value = true;
        bookingExternalReservationRequestResult.bookingReservationId = eventShowSeatsBookingResponse.getBookingReservationId();

        booking.setOfferCode(bookingConfirmationRequestDto.getOfferCode());
        booking.setFinalAmount(finalAmount);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        savePayment(booking);
        final Booking bookingUpdated = bookingRepository.save(booking);
        final BookingDetails bookingDetailsSaved = bookingDetailsRepository.save(bookingDetails);

        return bookingModelMapper.toBookingDetailsDto(bookingUpdated, bookingDetailsSaved);
    }

    /**
     * Confirm event show seats.
     *
     * @param bookingConfirmationRequestDto the booking confirmation request dto
     * @return the event show seats booking response dto
     */
    private EventShowSeatsBookingResponseDto confirmEventShowSeats(
            final BookingConfirmationRequestDto bookingConfirmationRequestDto) {

        final EventShowSeatsBookingResponseDto eventShowSeatsBookingResponse = bookingExternalApiHandlerService.book(
            EventShowSeatsBookingRequestDto.of(bookingConfirmationRequestDto.getEventShowId(),
                bookingConfirmationRequestDto.getBookingReservationId(),
                bookingConfirmationRequestDto.getSeatNumbers()));

        if (eventShowSeatsBookingResponse == null
            || StringUtils.isBlank(eventShowSeatsBookingResponse.getBookingReservationId())) {
            throw new SelectedSeatsUnavailableException();
        }
        return eventShowSeatsBookingResponse;
    }

    /**
     * Save payment.
     *
     * @param booking the booking
     */
    private void savePayment(final Booking booking) {

        final Payment payment = new Payment();
        payment.setAmount(booking.getFinalAmount());
        payment.setBooking(booking);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(Payment.DEFAULT_TRANSACTION_ID_PREFIX + "_" + booking.getBookingReservationId() + "_" + booking.getId());
        paymentRepository.save(payment);
    }

    /**
     * Cancel.
     *
     * @param bookingCancellationRequestDto the booking cancellation request dto
     */
    public void cancel(@NotNull(message = "The booking cannot be null") final BookingCancellationRequestDto bookingCancellationRequestDto) {
        BookingRequestValidator.validate(bookingCancellationRequestDto);
        validateUser(bookingCancellationRequestDto.getUsername(), bookingCancellationRequestDto.getPassword());

        if (bookingCancellationRequestDto.getBookingId() == null) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_ID);
        }

        cancel(findConfirmedBooking(bookingCancellationRequestDto.getUsername(), bookingCancellationRequestDto.getBookingId()));
    }

    /**
     * Finds the confirmed booking.
     *
     * @param username the username
     * @param bookingId the booking id
     * @return the booking
     */
    private Booking findConfirmedBooking(final String username, final Long bookingId) {
        final Booking booking = bookingRepository.findConfirmedBookingByUsernameAndId(username, bookingId);
        if (booking == null) {
            throw BookingNotFoundException.forUsernameAndIdAndConfirmedStatus(username, bookingId);
        }
        return booking;
    }

    /**
     * Cancel.
     *
     * @param booking the booking
     * @return true, if successful
     */
    @Transactional(rollbackFor = {Throwable.class})
    private void cancel(final Booking booking) {

        final BookingDetails bookingDetails = findBookingDetailsByBookingId(booking.getId());

        bookingExternalApiHandlerService.cancel(
            EventShowSeatsCancellationRequestDto.of(
                booking.getEventShowId(),
                bookingDetails.getSeatNumbers(),
                booking.getBookingReservationId().toString()));

        booking.setBookingStatus(BookingStatus.CANCELLED);
        final Booking bookingCancelled = bookingRepository.save(booking);
        final Payment payment = paymentRepository.findSuccessfulPaymentByBooking(bookingCancelled);
        if (payment == null) {
            throw new SuccessfulPaymentNotFoundForBookingException(bookingCancelled.getId());
        }
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);
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

        final Booking booking = findBooking(username, id);
        final BookingDetails bookingDetails = findBookingDetailsByBookingId(id);

        return bookingModelMapper.toBookingDetailsDto(booking, bookingDetails);
    }

    /**
     * Finds the booking.
     *
     * @param username the username
     * @param id the id
     * @return the booking
     */
    private Booking findBooking(final String username, final Long id) {

        final Booking booking = bookingRepository.findByUsernameAndId(username, id);
        if (booking == null) {
            throw BookingNotFoundException.forUsernameAndId(username, id);
        }
        return booking;
    }

    /**
     * Finds the by username.
     *
     * @param username the username
     * @return the booking details dto list
     */
    public BookingDetailsDtoList findByUsername(@NotBlank(message = "The username cannot be blank.") final String username) {

        final List<Booking> bookings = bookingRepository.findByUsername(username);
        if (CollectionUtils.isEmpty(bookings)) {
            throw BookingNotFoundException.forUsername(username);
        }

        return new BookingDetailsDtoList(bookings.stream().map(booking -> bookingModelMapper.toBookingDetailsDto(booking,
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
            throw new BookingServiceException(Messages.MESSAGE_ERROR_INVALID_REQUEST);
        }

        final EventShowSeatInformationResponseDtoList eventShowSeatsForReservation = bookingExternalApiHandlerService
            .findEventShowSeatsByBookingReservation(bookingConfirmationRequestDto.getEventShowId(), optionalBooking.get()
                .getBookingReservationId().toString());

        if (eventShowSeatsForReservation.getDtos().size() != bookingConfirmationRequestDto.getSeatNumbers().size()
            || bookingConfirmationRequestDto.getSeatNumbers()
                .stream().anyMatch(seatNumber -> !eventShowSeatsForReservation.getDtos().stream().map(
                    EventShowSeatInformationResponseDto::getSeatNumber).collect(Collectors.toSet()).contains(seatNumber))) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_INVALID_SEATS_FOR_RESERVATION);
        }

        if (!BookingStatus.RESERVED.equals(optionalBooking.get().getBookingStatus())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_INVALID_BOOKING_FOR_CONFIRMATION);
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
            throw new BookingServiceException(Messages.MESSAGE_ERROR_INVALID_EVENT_SHOW_ID, eventShowId);
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
            throw new BookingServiceException(Messages.MESSAGE_ERROR_MINIMUM_ONE_SEAT);
        }

        final int maxSeatsPerBooking = EnvironmentProperties.get(environment()).maxSeatsPerBooking();
        if (bookingRequestSeatNumbers.size() > maxSeatsPerBooking) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_MAX_SEATS_PER_BOOKING, maxSeatsPerBooking);
        }

        final EventShowSeatForShowResponseDtoList eventShowSeatListForShow = bookingExternalApiHandlerService
            .findEventShowSeatsForShow(eventShowId);

        final Map<String, List<EventShowSeatForShowResponseDto>> eventShowSeatsBySeatNumberMap = eventShowSeatListForShow.getDtos()
            .stream().collect(Collectors.groupingBy(EventShowSeatForShowResponseDto::getSeatNumber));

        if (isInvalidSeatsSelection(bookingRequestSeatNumbers, eventShowSeatsBySeatNumberMap)) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_INVALID_SEATS_SELECTED);
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
                .getSeatReservationStatus(), SEAT_RESERVATION_STATUS_AVAILABLE));
    }
}
