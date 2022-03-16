/*
* Id: BookingModelMapper.java 16-Mar-2022 8:48:14 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.service.modelmapper;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationResponseDto;
import com.xyz.apps.ticketeer.booking.model.Booking;
import com.xyz.apps.ticketeer.booking.model.BookingStatus;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;

/**
 * The booking model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class BookingModelMapper {

    /**
     * Instantiates a new booking model mapper.
     */
    public BookingModelMapper() {

    }

    /**
     * To reserved booking.
     *
     * @param bookingReservationRequestDto the booking dto
     * @param bookingReservationId the booking reservation id
     * @param baseAmountOfSeats the base amount of seats
     * @param cityId the city id
     * @return the booking
     */
    public Booking toReservedBooking(
            final BookingReservationRequestDto bookingReservationRequestDto,
            final String bookingReservationId,
            final Double baseAmountOfSeats,
            final Long cityId) {

        final Booking booking = new Booking();
        booking.setBookingReservationId(UUID.fromString(bookingReservationId));
        booking.setReservationTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.RESERVED);
        booking.setNumberOfSeats(bookingReservationRequestDto.getSeatNumbers().size());
        booking.setAmount(baseAmountOfSeats);
        booking.setFinalAmount(booking.getAmount());
        booking.setCityId(cityId);
        booking.setEventShowId(bookingReservationRequestDto.getEventShowId());
        booking.setUsername(bookingReservationRequestDto.getUsername());
        booking.setPhoneNumber(bookingReservationRequestDto.getPhoneNumber());
        booking.setEmailId(bookingReservationRequestDto.getEmailId());

        return booking;
    }

    /**
     * Of.
     *
     * @param booking the booking
     * @param seatNumbers the seat numbers
     * @return the booking reservation response dto
     */
    public BookingReservationResponseDto of(final Booking booking, final Set<String> seatNumbers) {
        final BookingReservationResponseDto bookingReservationResponseDto = new BookingReservationResponseDto();
        if (booking != null) {
            bookingReservationResponseDto.setBookingId(booking.getId());
            bookingReservationResponseDto.setBookingReservationId(booking.getBookingReservationId().toString());
            bookingReservationResponseDto.setBookingStatus(booking.getBookingStatus().name());
            bookingReservationResponseDto.setReservationTime(LocalDateTimeFormatUtil.format(booking.getReservationTime()));
            bookingReservationResponseDto.setEventShowId(booking.getEventShowId());
            bookingReservationResponseDto.setSeatNumbers(seatNumbers);
            bookingReservationResponseDto.setUsername(booking.getUsername());
            bookingReservationResponseDto.setPhoneNumber(booking.getPhoneNumber());
            bookingReservationResponseDto.setEmailId(booking.getEmailId());
        }
        return bookingReservationResponseDto;
    }
}
