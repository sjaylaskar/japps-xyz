/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.service;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;

import com.xyz.apps.ticketeer.booking.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.booking.api.external.contract.BasicUserDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDto;
import com.xyz.apps.ticketeer.booking.model.Booking;
import com.xyz.apps.ticketeer.booking.model.BookingRepository;
import com.xyz.apps.ticketeer.booking.model.BookingStatus;
import com.xyz.apps.ticketeer.booking.model.Payment;
import com.xyz.apps.ticketeer.booking.model.PaymentRepository;
import com.xyz.apps.ticketeer.booking.model.PaymentStatus;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;
import com.xyz.apps.ticketeer.util.StringUtil;


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

    /**
     * Reserve.
     *
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public BookingDto reserve(final BookingDto bookingDto) {

        validateBooking(bookingDto);

        if (BooleanUtils.isTrue(areEventShowSeatsAvailable(bookingDto))) {

            Integer reservedBookingSeatCount = reserveSeats(bookingDto);
            if (reservedBookingSeatCount != bookingDto.getEventShowSeatIds().size()) {

                unreserveSeats(bookingDto);

                throw new SelectedSeatsUnavailableException();
            }

            final Booking booking = bookingRepository.save(toReservedBooking(bookingDto));

            final EventShowSeatsBookingDto eventShowSeatsBookingDto = new EventShowSeatsBookingDto();
            eventShowSeatsBookingDto.setBookingId(booking.getId());
            eventShowSeatsBookingDto.setSeatIds(bookingDto.getEventShowSeatIds());

            reservedBookingSeatCount = reserveSeatsWithBooking(eventShowSeatsBookingDto);
            if (reservedBookingSeatCount != booking.getNumberOfSeats()) {
                cancelBookingForEventShowSeats(booking.getId());
                throw new SelectedSeatsUnavailableException();
            }
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
    @Transactional(rollbackFor = {Throwable.class})
    public BookingDto confirm(final BookingDto bookingDto) {

        validateBooking(bookingDto);

        final Booking booking = bookingRepository.findById(bookingDto.getBookingId()).orElseThrow(BookingExpiredException::new);

        final EventShowSeatsBookingDto eventShowSeatsBookingDto = new EventShowSeatsBookingDto();
        eventShowSeatsBookingDto.setBookingId(booking.getId());
        eventShowSeatsBookingDto.setSeatIds(bookingDto.getEventShowSeatIds());


        if (!areEventShowSeatsReserved(eventShowSeatsBookingDto)) {
            throw new SelectedSeatsUnavailableException();
        }

        final double baseAmount = calculateEventShowSeatsAmount(bookingDto);

        bookingDto.setAmount(baseAmount);
        bookingDto.setFinalAmount(baseAmount);

        final BookingPriceInfoDto bookingPriceInfoDto = toBookingPriceInfo(bookingDto);

        final double finalAmount = calculateFinalBookingAmount(bookingPriceInfoDto);

        final Payment payment = new Payment();
        payment.setAmount(finalAmount);
        payment.setBooking(booking);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        booking.setAmount(baseAmount);
        booking.setFinalAmount(finalAmount);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingTime(LocalDateTimeFormatUtil.parseLocalDateTime(bookingPriceInfoDto.getBookingTime()));

        final Booking bookingUpdated = bookingRepository.save(booking);

        // TODO - save booking details.
        // Controller -> getById, getByUserId. (CONFIRMED and CANCELLED).
        // TEST reserve, confirm, cancel, same-seat-contention, getById, getByUserId
        // Discount add, booking amount on offer code, getDiscount by city, venue, etc.
        // Documents (2).

        final int countOfBookedSeats = bookSeats(eventShowSeatsBookingDto);

        if (countOfBookedSeats != bookingDto.getEventShowSeatIds().size()) {
            cancelBookingForEventShowSeats(booking.getId());
            throw new SelectedSeatsUnavailableException();
        }

        bookingDto.setAmount(baseAmount);
        bookingDto.setFinalAmount(finalAmount);
        bookingDto.setBookingStatus(bookingUpdated.getBookingStatus() != null ? bookingUpdated.getBookingStatus().name() : null);
        bookingDto.setBookingTime(booking.getBookingTime().toString());
        bookingDto.setIsConfirmed(BookingStatus.CONFIRMED.equals(booking.getBookingStatus()));

        return bookingDto;
    }

    /**
     * Cancel.
     *
     * @param bookingDto the booking dto
     * @return true, if successful
     */
    @Transactional(rollbackFor = {Throwable.class})
    public boolean cancel(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        validateBooking(bookingDto);

        final Booking booking = bookingRepository.findById(bookingDto.getBookingId()).orElseThrow(
            () -> new BookingNotFoundException(bookingDto.getBookingId()));
        booking.setBookingStatus(BookingStatus.CANCELLED);
        final Booking bookingCancelled = bookingRepository.save(booking);
        final Payment payment = paymentRepository.findSuccessfulPaymentByBooking(bookingCancelled);
        if (payment == null) {
            throw new SuccessfulPaymentNotFoundForBookingException(bookingDto.getBookingId());
        }
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        cancelBookingForEventShowSeats(bookingDto.getBookingId());

        return true;
    }

    /**
     * Reserve seats with booking.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the count of seats reserved.
     */
    private Integer reserveSeatsWithBooking(final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        ResponseEntity<Integer> eventShowSeatsBookingReservedCountResponseEntity = null;
        try {
            eventShowSeatsBookingReservedCountResponseEntity = serviceBeansFetcher().restTemplate().exchange(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_RESERVE_WITH_BOOKING.get()),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsBookingDto>(eventShowSeatsBookingDto),
                Integer.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return hasBodyResponseEntity(eventShowSeatsBookingReservedCountResponseEntity) ? eventShowSeatsBookingReservedCountResponseEntity.getBody() : 0;
    }

    /**
     * Are event show seats available.
     *
     * @param bookingDto the booking dto
     * @return {@code true}, if event show seats are available.
     */
    private Boolean areEventShowSeatsAvailable(final BookingDto bookingDto) {

        ResponseEntity<Boolean> eventShowSeatsAreAvailableResponseEntity = null;
        try {
            eventShowSeatsAreAvailableResponseEntity = serviceBeansFetcher().restTemplate().postForEntity(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_ARE_AVAILABLE.get()), bookingDto.getEventShowSeatIds(), Boolean.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return hasBodyResponseEntity(eventShowSeatsAreAvailableResponseEntity) ? eventShowSeatsAreAvailableResponseEntity.getBody() : false;
    }

    /**
     * Unreserve seats.
     *
     * @param bookingDto the booking dto
     */
    private void unreserveSeats(final BookingDto bookingDto) {

        try {
            serviceBeansFetcher().restTemplate().put(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_UNRESERVE.get()), bookingDto.getEventShowSeatIds());
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }
    }

    /**
     * Reserve seats.
     *
     * @param bookingDto the booking dto
     * @return the count of seats reserved.
     */
    private Integer reserveSeats(final BookingDto bookingDto) {

        ResponseEntity<Integer> eventShowSeatsReservedCountResponseEntity = null;
        try {
            eventShowSeatsReservedCountResponseEntity = serviceBeansFetcher().restTemplate().exchange(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_RESERVE.get()),
                HttpMethod.PUT,
                new HttpEntity<Set<Long>>(bookingDto.getEventShowSeatIds()),
                Integer.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return hasBodyResponseEntity(eventShowSeatsReservedCountResponseEntity) ? eventShowSeatsReservedCountResponseEntity.getBody() : 0;
    }

    /**
     * Are event show seats reserved.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return {@code true}, if event show seats are reserved for booking.
     */
    private Boolean areEventShowSeatsReserved(final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        ResponseEntity<Boolean> eventShowSeatsAreReservedResponseEntity = null;
        try {
            eventShowSeatsAreReservedResponseEntity = serviceBeansFetcher().restTemplate().postForEntity(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_ARE_RESERVED.get()), eventShowSeatsBookingDto, Boolean.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return hasBodyResponseEntity(eventShowSeatsAreReservedResponseEntity) ? eventShowSeatsAreReservedResponseEntity.getBody() : false;
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

        final Double amount = calculateEventShowSeatsAmount(bookingDto);

        final Booking booking = new Booking();
        booking.setReservationTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.RESERVED);
        booking.setNumberOfSeats(bookingDto.getEventShowSeatIds().size());
        booking.setAmount(amount);
        booking.setFinalAmount(amount);
        booking.setOfferCode(bookingDto.getOfferCode());
        booking.setEventShowId(bookingDto.getEventShowId());
        booking.setUsername(bookingDto.getUsername());
        booking.setPhoneNumber(bookingDto.getPhoneNumber());
        booking.setEmailId(bookingDto.getEmailId());

        return booking;
    }

    /**
     * Calculate event show seats amount.
     *
     * @param bookingDto the booking dto
     * @return the amount
     */
    private Double calculateEventShowSeatsAmount(final BookingDto bookingDto) {

        ResponseEntity<Double> eventShowSeatsAmountResponseEntity = null;
        try {
            eventShowSeatsAmountResponseEntity = serviceBeansFetcher().restTemplate().postForEntity(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.CALCULATE_EVENT_SHOW_SEATS_AMOUNT.get()), bookingDto.getEventShowSeatIds(), Double.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        if (hasBodyResponseEntity(eventShowSeatsAmountResponseEntity)) {
            throw new BookingServiceException("Failed to calculate seats amount for booking: " + bookingDto);
        }

        return eventShowSeatsAmountResponseEntity.getBody();
    }

    /**
     * Calculate final booking amount.
     *
     * @param bookingPriceInfoDto the booking price info dto
     * @return the amount
     */
    private Double calculateFinalBookingAmount(final BookingPriceInfoDto bookingPriceInfoDto) {

        ResponseEntity<Double> bookingFinalAmountResponseEntity = null;
        try {
            bookingFinalAmountResponseEntity = serviceBeansFetcher().restTemplate().postForEntity(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.PRICING_CALCULATE.get()), bookingPriceInfoDto, Double.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        if (hasBodyResponseEntity(bookingFinalAmountResponseEntity)) {
            throw new BookingServiceException("Failed to calculate final amount for booking.");
        }

        return bookingFinalAmountResponseEntity.getBody();
    }

    /**
     * Book seats.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the count of booked seats
     */
    private Integer bookSeats(final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        ResponseEntity<Integer> eventShowSeatsBookedCountResponseEntity = null;
        try {
            eventShowSeatsBookedCountResponseEntity = serviceBeansFetcher().restTemplate().exchange(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_BOOK.get()),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsBookingDto>(eventShowSeatsBookingDto),
                Integer.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return hasBodyResponseEntity(eventShowSeatsBookedCountResponseEntity) ? eventShowSeatsBookedCountResponseEntity.getBody() : 0;
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
        final EventShowDto eventShow = findEventShow(bookingDto);
        bookingPriceInfoDto.setEventShowId(bookingDto.getEventShowId());
        bookingPriceInfoDto.setShowDate(eventShow.getDate());
        bookingPriceInfoDto.setShowStartTime(eventShow.getStartTime());
        bookingPriceInfoDto.setBookingTime(LocalDateTimeFormatUtil.format(LocalDateTime.now()));
        return bookingPriceInfoDto;
    }

    /**
     * Cancel booking for event show seats.
     *
     * @param bookingId the booking id
     */
    private void cancelBookingForEventShowSeats(final Long bookingId) {

        try {
            serviceBeansFetcher().restTemplate().put(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_CANCEL.get()), bookingId);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }
    }

    /**
     * Validate booking.
     *
     * @param bookingDto the booking dto
     */
    private void validateBooking(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        validateEventShowSeats(bookingDto);

        validateUser(bookingDto);

        validateEventShow(bookingDto);

        validateCity(bookingDto);
    }

    /**
     * Validate city.
     *
     * @param bookingDto the booking dto
     */
    private void validateCity(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        if (bookingDto.getCityId() == null) {
            throw new BookingServiceException("The city id cannot be null.");
        }

        try {
            serviceBeansFetcher().restTemplate().getForEntity(
                StringUtil.format(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_CITY_BY_ID.get()), bookingDto
                    .getCityId()), CityDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }
    }

    /**
     * Validate user.
     *
     * @param bookingDto the booking dto
     */
    private void validateUser(final BookingDto bookingDto) {

        if (StringUtils.isBlank(bookingDto.getUsername()) || StringUtils.isBlank(bookingDto.getPassword())) {
            throw new BookingServiceException("User is not authenticated.");
        }

        final BasicUserDto basicUserDto = new BasicUserDto();
        basicUserDto.setUsername(bookingDto.getUsername());
        basicUserDto.setPassword(bookingDto.getPassword());

        try {
            serviceBeansFetcher().restTemplate().postForEntity(
                serviceBeansFetcher().environment().getProperty(ApiPropertyKey.AUTHENTICATE_USER.get()), basicUserDto,
                Boolean.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException("User is not authorized.");
        }
    }

    /**
     * Validate event show.
     *
     * @param bookingDto the booking dto
     */
    private void validateEventShow(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        findEventShow(bookingDto);
    }

    /**
     * Finds the event show.
     *
     * @param bookingDto the booking dto
     * @return the event show dto
     */
    private EventShowDto findEventShow(final BookingDto bookingDto) {

        if (bookingDto.getEventShowId() == null) {
            throw new BookingServiceException("The event show id cannot be null.");
        }
        ResponseEntity<EventShowDto> eventShowDtoResponseEntity = null;
        try {
            eventShowDtoResponseEntity = serviceBeansFetcher().restTemplate().getForEntity(
                StringUtil.format(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_EVENT_SHOW_BY_ID.get()),
                    bookingDto.getEventShowId()),
                EventShowDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        if (eventShowDtoResponseEntity == null || !eventShowDtoResponseEntity.hasBody()) {
            throw new BookingServiceException("Invalid event show id: " + bookingDto.getEventShowId());
        }
        return eventShowDtoResponseEntity.getBody();
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
    }

    /**
     * Indicates if has body response entity.
     *
     * @param <T> the generic type
     * @param responseEntity the response entity
     * @return true, if successful
     */
    private static <T> boolean hasBodyResponseEntity(final ResponseEntity<T> responseEntity) {
        return responseEntity != null && responseEntity.hasBody();
    }
}
