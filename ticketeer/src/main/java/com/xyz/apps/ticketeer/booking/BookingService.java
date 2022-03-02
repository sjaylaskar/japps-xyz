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

import com.xyz.apps.ticketeer.eventshow.EventShowSeatRepository;

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

    /** The event show seat repository. */
    @Autowired
    private EventShowSeatRepository eventShowSeatRepository;

    /**
     * Reserve.
     *
     * @param bookingDto the booking dto
     * @return the booking dto
     */
    @Transactional
    public BookingDto reserve(final BookingDto bookingDto) {
        if (eventShowSeatRepository.areSeatsAvailable(bookingDto.getEventShowSeatIds(), bookingDto.getEventShowSeatIds().size())) {
            int reservedBookingSeatCount = eventShowSeatRepository.reserveSeats(bookingDto.getEventShowSeatIds(), bookingDto.getEventShowSeatIds().size());
            if (reservedBookingSeatCount != bookingDto.getEventShowSeatIds().size()) {
                throw new SelectedSeatsUnavailableException();
            }
            final Booking booking = bookingRepository.save(toReservedBooking(bookingDto));
            reservedBookingSeatCount = eventShowSeatRepository.fillBookingForReservedSeats(bookingDto.getEventShowSeatIds(), booking.getNumberOfSeats(), booking.getId());
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
        bookingDto.setReservationTime(booking.getReservationTime().toString());
        bookingDto.setReserved(BookingStatus.RESERVED.equals(booking.getBookingStatus()));
        bookingDto.setBookingStatus(booking.getBookingStatus().name());
        return bookingDto;
    }

    /**
     * To reserved booking.
     *
     * @param bookingDto the booking dto
     * @return the booking
     */
    private Booking toReservedBooking(final BookingDto bookingDto) {
        return
        new Booking(null,
            LocalDateTime.now(),
            BookingStatus.RESERVED,
            bookingDto.getEventShowSeatIds().size(),
            bookingDto.getAmount(),
            bookingDto.getFinalAmount(),
            bookingDto.getOfferCode(),
            bookingDto.getUser(),
            bookingDto.getEventShow());
    }

    @Transactional
    public BookingDto confirm(final BookingDto bookingDto) {
        if (eventShowSeatRepository.areSeatsReserved(bookingDto.getEventShowSeatIds(), bookingDto.getBookingId(), bookingDto.getEventShowSeatIds().size())) {

        }

        throw new SelectedSeatsUnavailableException();
    }

    /**
     * Cancel.
     *
     * @param bookingId the booking id
     * @return true, if successful
     */
    @Transactional
    public boolean cancel(final Long bookingId) {
        bookingRepository.cancelById(bookingId);
        eventShowSeatRepository.cancelByBookingId(bookingId);
        bookingRepository.refundPaymentById(bookingId);

        return true;
    }
}
