/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.apps.ticketeer.eventshow.EventShow;
import com.xyz.apps.ticketeer.eventshow.EventShowSeatRepository;
import com.xyz.apps.ticketeer.pricing.PricingService;
import com.xyz.apps.ticketeer.user.User;


/**
 * The booking service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class BookingService {

    /** The booking repository. */
    @Autowired
    private BookingRepository bookingRepository;

    /** The payment repository. */
    @Autowired
    private PaymentRepository paymentRepository;

    /** The event show seat repository. */
    @Autowired
    private EventShowSeatRepository eventShowSeatRepository;

    /** The pricing service. */
    @Autowired
    private PricingService pricingService;

    // @TODO - Connect with actual payment gateway interface.
    /** The default payment method. */
    private static final String DEFAULT_PAYMENT_METHOD = "UPI";

    /** The default transaction id. */
    private static final String DEFAULT_TRANSACTION_ID = "TRN1234";

    /**
     * Reserve.
     *
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    @Transactional(rollbackOn = {Throwable.class})
    public BookingDto reserve(final BookingDto bookingDto) {

        if (eventShowSeatRepository.areSeatsAvailable(bookingDto.getEventShowSeatIds(), bookingDto.getEventShowSeatIds().size())) {
            int reservedBookingSeatCount = eventShowSeatRepository.reserveSeats(bookingDto.getEventShowSeatIds(), bookingDto
                .getEventShowSeatIds().size());
            if (reservedBookingSeatCount != bookingDto.getEventShowSeatIds().size()) {
                throw new SelectedSeatsUnavailableException();
            }
            final Booking booking = bookingRepository.save(toReservedBooking(bookingDto));
            reservedBookingSeatCount = eventShowSeatRepository.fillBookingForReservedSeats(bookingDto.getEventShowSeatIds(), booking
                .getNumberOfSeats(), booking.getId());
            if (reservedBookingSeatCount != booking.getNumberOfSeats()) {
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
        bookingDto.setReserved(BookingStatus.RESERVED.equals(booking.getBookingStatus()));
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

        final double amount = pricingService.calculateBaseAmount(bookingDto);
        return new Booking(null,
            LocalDateTime.now(),
            BookingStatus.RESERVED,
            bookingDto.getEventShowSeatIds().size(),
            amount,
            amount,
            bookingDto.getOfferCode(),
            User.builder().id(bookingDto.getUserId()).build(),
            EventShow.builder().id(bookingDto.getEventShowId()).build());
    }

    /**
     * Confirm.
     *
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    @Transactional(rollbackOn = {Throwable.class})
    public BookingDto confirm(final BookingDto bookingDto) {

        if (!bookingRepository.existsById(bookingDto.getBookingId())) {
            throw new BookingExpiredException();
        }
        if (!eventShowSeatRepository.areSeatsReserved(bookingDto.getEventShowSeatIds(), bookingDto.getBookingId(), bookingDto
            .getEventShowSeatIds().size())) {
            throw new SelectedSeatsUnavailableException();
        }

        final int countOfBookedSeats = eventShowSeatRepository.bookSeats(bookingDto.getEventShowSeatIds(), bookingDto.getEventShowSeatIds().size(), bookingDto.getBookingId());

        if (countOfBookedSeats != bookingDto.getEventShowSeatIds().size()) {
            throw new SelectedSeatsUnavailableException();
        }

        final double baseAmount = pricingService.calculateBaseAmount(bookingDto);
        final double finalAmount = pricingService.calculateFinalAmount(bookingDto);

        paymentRepository.save(
            Payment.builder()
                .amount(finalAmount)
                .booking(Booking.builder().id(bookingDto.getBookingId()).build())
                .paymentMethod(DEFAULT_PAYMENT_METHOD)
                .paymentStatus(PaymentStatus.SUCCESS)
                .build());
        final Booking booking = bookingRepository.findById(bookingDto.getBookingId()).orElseThrow(BookingExpiredException::new);

        booking.setAmount(baseAmount);
        booking.setFinalAmount(finalAmount);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingTime(LocalDateTime.now());

        final Booking bookingUpdated = bookingRepository.save(booking);

        bookingDto.setAmount(baseAmount);
        bookingDto.setFinalAmount(finalAmount);
        bookingDto.setBookingStatus(bookingUpdated.getBookingStatus() != null ? bookingUpdated.getBookingStatus().name() : null);
        bookingDto.setBookingTime(booking.getBookingTime().toString());
        bookingDto.setConfirmed(BookingStatus.CONFIRMED.equals(booking.getBookingStatus()));

        return bookingDto;
    }

    /**
     * Cancel.
     *
     * @param bookingId the booking id
     * @return true, if successful
     */
    @Transactional(rollbackOn = {Throwable.class})
    public boolean cancel(final Long bookingId) {
        final Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        eventShowSeatRepository.cancelByBookingId(bookingId);
        final Payment payment = paymentRepository.findSuccessfulPaymentByBookingId(bookingId);
        if (payment == null) {
            throw new SuccessfulPaymentNotFoundForBookingException(bookingId);
        }
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        return true;
    }
}
