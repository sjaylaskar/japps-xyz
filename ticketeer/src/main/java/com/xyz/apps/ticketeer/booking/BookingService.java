/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.xyz.apps.ticketeer.booking.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.booking.api.external.contract.BasicUserDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingDto;
import com.xyz.apps.ticketeer.util.Environment;
import com.xyz.apps.ticketeer.util.WebClientBuilder;

import reactor.core.publisher.Mono;


/**
 * The booking service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class BookingService {

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

        if (WebClientBuilder.get().build()
                .post()
                .uri(Environment.property(ApiPropertyKey.EVENT_SHOW_SEATS_ARE_AVAILABLE.get()))
                .body(Mono.just(bookingDto.getEventShowSeatIds()), Set.class)
                .retrieve()
                .bodyToMono(Boolean.class).block()) {

            int reservedBookingSeatCount = WebClientBuilder.get().build()
                    .put()
                    .uri(Environment.property(ApiPropertyKey.EVENT_SHOW_SEATS_RESERVE.get()))
                    .body(Mono.just(bookingDto.getEventShowSeatIds()), Set.class)
                    .retrieve()
                    .bodyToMono(Integer.class).block();
            if (reservedBookingSeatCount != bookingDto.getEventShowSeatIds().size()) {
                WebClientBuilder.get().build()
                .put()
                .uri(Environment.property(ApiPropertyKey.EVENT_SHOW_SEATS_UNRESERVE.get()))
                .body(Mono.just(bookingDto.getEventShowSeatIds()), Set.class)
                .retrieve()
                .bodyToMono(Integer.class).block();
                throw new SelectedSeatsUnavailableException();
            }

            final Booking booking = bookingRepository.save(toReservedBooking(bookingDto));

            final EventShowSeatsBookingDto eventShowSeatsBookingDto = new EventShowSeatsBookingDto();
            eventShowSeatsBookingDto.setBookingId(booking.getId());
            eventShowSeatsBookingDto.setSeatIds(bookingDto.getEventShowSeatIds());

            reservedBookingSeatCount = WebClientBuilder.get().build()
                    .put()
                    .uri(Environment.property(ApiPropertyKey.EVENT_SHOW_SEATS_RESERVE_WITH_BOOKING.get()))
                    .body(Mono.just(eventShowSeatsBookingDto), EventShowSeatsBookingDto.class)
                    .retrieve()
                    .bodyToMono(Integer.class).block();
            if (reservedBookingSeatCount != booking.getNumberOfSeats()) {
                cancelBookingForEventShowSeats(booking.getId());
                throw new SelectedSeatsUnavailableException();
            }
            return toReservedBookingDto(booking, bookingDto);
        }

        throw new SelectedSeatsUnavailableException();
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


        final Double amount = WebClientBuilder.get().build()
                .post()
                .uri(Environment.property(ApiPropertyKey.CALCULATE_EVENT_SHOW_SEATS_AMOUNT.get()))
                .body(Mono.just(bookingDto.getEventShowSeatIds()), Set.class)
                .retrieve()
                .bodyToMono(Double.class).block();

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

        if (!WebClientBuilder.get().build()
                .post()
                .uri(Environment.property(ApiPropertyKey.EVENT_SHOW_SEATS_ARE_RESERVED.get()))
                .body(Mono.just(eventShowSeatsBookingDto), EventShowSeatsBookingDto.class)
                .retrieve()
                .bodyToMono(Boolean.class).block()) {
            throw new SelectedSeatsUnavailableException();
        }

        final double baseAmount = WebClientBuilder.get().build()
                .post()
                .uri(Environment.property(ApiPropertyKey.CALCULATE_EVENT_SHOW_SEATS_AMOUNT.get()))
                .body(Mono.just(bookingDto.getEventShowSeatIds()), Set.class)
                .retrieve()
                .bodyToMono(Double.class).block();

        bookingDto.setAmount(baseAmount);
        bookingDto.setFinalAmount(baseAmount);

        final BookingPriceInfoDto bookingPriceInfoDto = toBookingPriceInfo(bookingDto);

        final double finalAmount = WebClientBuilder.get().build()
                .post()
                .uri(Environment.property(ApiPropertyKey.PRICING_CALCULATE.get()))
                .body(Mono.just(bookingPriceInfoDto), BookingPriceInfoDto.class)
                .retrieve()
                .bodyToMono(Double.class).block();

        final Payment payment = new Payment();
        payment.setAmount(finalAmount);
        payment.setBooking(booking);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        booking.setAmount(baseAmount);
        booking.setFinalAmount(finalAmount);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingTime(LocalDateTime.parse(bookingPriceInfoDto.getBookingTime()));

        final Booking bookingUpdated = bookingRepository.save(booking);

        final int countOfBookedSeats = WebClientBuilder.get().build()
                .post()
                .uri(Environment.property(ApiPropertyKey.EVENT_SHOW_SEATS_BOOK.get()))
                .body(Mono.just(eventShowSeatsBookingDto), EventShowSeatsBookingDto.class)
                .retrieve()
                .bodyToMono(Integer.class).block();

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
        bookingPriceInfoDto.setShowStartTime(bookingDto.getShowStartTime());
        bookingPriceInfoDto.setBookingTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return bookingPriceInfoDto;
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

        final Booking booking = bookingRepository.findById(bookingDto.getBookingId()).orElseThrow(() -> new BookingNotFoundException(bookingDto.getBookingId()));
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
     * Cancel booking for event show seats.
     *
     * @param bookingId the booking id
     * @return the response spec
     */
    private ResponseSpec cancelBookingForEventShowSeats(final Long bookingId) {

        return WebClientBuilder.get().build()
        .post()
        .uri(Environment.property(ApiPropertyKey.EVENT_SHOW_SEATS_CANCEL.get()))
        .body(Mono.just(bookingId), Long.class)
        .retrieve().onStatus(status -> HttpStatus.OK.value() != status.value(),
                             response -> Mono.error(new BookingServiceException(response.bodyToMono(String.class).block())));
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
        if (bookingDto.getCityId() == null) {
            throw new BookingServiceException("The city id cannot be null.");
        }

        WebClientBuilder.get().build()
        .get()
        .uri(Environment.property(ApiPropertyKey.GET_CITY_BY_ID.get(bookingDto.getCityId())))
        .retrieve()
        .onStatus(status -> HttpStatus.FOUND.value() != status.value(),
                  response -> Mono.error(new BookingServiceException(response.bodyToMono(String.class).block())));

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

        WebClientBuilder.get().build()
        .post()
        .uri(Environment.property(ApiPropertyKey.AUTHENTICATE_USER.get()))
        .body(Mono.just(basicUserDto), BasicUserDto.class)
        .retrieve()
        .onStatus(status -> HttpStatus.OK.value() != status.value(),
                  response -> Mono.error(new BookingServiceException(response.bodyToMono(String.class).block())));
    }

    /**
     * Validate event show.
     *
     * @param bookingDto the booking dto
     */
    private void validateEventShow(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {
        if (bookingDto.getEventShowId() == null) {
            throw new BookingServiceException("The event show id cannot be null.");
        }

        WebClientBuilder.get().build()
        .get()
        .uri(Environment.property(ApiPropertyKey.GET_EVENT_SHOW_BY_ID.get(bookingDto.getEventShowId())))
        .retrieve()
        .onStatus(status -> HttpStatus.FOUND.value() != status.value(),
                  response -> Mono.error(new BookingServiceException(response.bodyToMono(String.class).block())));

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
    }
}
