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

import com.xyz.apps.ticketeer.booking.api.external.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDetailedInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatNumberPriceDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatPricesResponseDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingConfirmationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDetailsDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationResponseDto;
import com.xyz.apps.ticketeer.booking.model.Booking;
import com.xyz.apps.ticketeer.booking.model.BookingDetails;
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

    /**
     * To booking details dto.
     *
     * @param booking the booking
     * @param bookingDetails the booking details
     * @return the booking details dto
     */
    public BookingDetailsDto toBookingDetailsDto(final Booking booking, final BookingDetails bookingDetails) {

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
    public void fillBookingDetailsInfo(final BookingDetails bookingDetails, final BookingDetailsDto bookingDetailsDto) {

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
     * @param bookingConfirmationRequestDto the booking dto
     * @param eventShowSeatPricesResponseDto the event show seat prices response dto
     * @param eventShowDetailedInfoDto the event show detailed info dto
     * @return the booking details
     */
    public BookingDetails toBookingDetails(
            final BookingConfirmationRequestDto bookingConfirmationRequestDto,
            final EventShowSeatPricesResponseDto eventShowSeatPricesResponseDto,
            final EventShowDetailedInfoDto eventShowDetailedInfoDto) {

        final BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setBookingId(bookingConfirmationRequestDto.getBookingId());
        bookingDetails.setEventShowDate(LocalDateTimeFormatUtil.parseLocalDate(eventShowDetailedInfoDto.getDate()));
        bookingDetails.setEventShowTime(LocalDateTimeFormatUtil.parseLocalTime(eventShowDetailedInfoDto.getStartTime()));
        bookingDetails.setCityName(eventShowDetailedInfoDto.getCityName());
        bookingDetails.setEventName(eventShowDetailedInfoDto.getEventName());
        bookingDetails.setEventVenueName(eventShowDetailedInfoDto.getEventVenueName());
        bookingDetails.setAuditoriumName(eventShowDetailedInfoDto.getAuditoriumName());
        bookingDetails.setSeatNumbers(bookingConfirmationRequestDto.getSeatNumbers());
        bookingDetails.setSeatBaseAmounts(eventShowSeatPricesResponseDto.getEventShowSeatNumberPrices().stream().map(
            EventShowSeatNumberPriceDto::getAmount).toList());
        return bookingDetails;
    }

    /**
     * To booking price info.
     *
     * @param bookingConfirmationRequestDto the booking dto
     * @param eventShow the event show
     * @param bookingTime the booking time
     * @param eventShowSeatPrices the event show seat prices
     * @return the booking price info
     */
    public BookingPriceInfoDto toBookingPriceInfo(final BookingConfirmationRequestDto bookingConfirmationRequestDto,
            final EventShowDto eventShow, final String bookingTime, final EventShowSeatPricesResponseDto eventShowSeatPrices) {

        final BookingPriceInfoDto bookingPriceInfoDto = new BookingPriceInfoDto();
        bookingPriceInfoDto.setSeatBasePrices(eventShowSeatPrices.getEventShowSeatNumberPrices().stream().map(
            EventShowSeatNumberPriceDto::getAmount).toList());
        bookingPriceInfoDto.setOfferCode(bookingConfirmationRequestDto.getOfferCode());
        bookingPriceInfoDto.setCityId(eventShow.getCityId());
        bookingPriceInfoDto.setEventVenueId(eventShow.getEventVenueId());
        bookingPriceInfoDto.setEventId(eventShow.getEventId());
        bookingPriceInfoDto.setEventShowId(bookingConfirmationRequestDto.getEventShowId());
        bookingPriceInfoDto.setShowDate(eventShow.getDate());
        bookingPriceInfoDto.setShowStartTime(eventShow.getStartTime());
        bookingPriceInfoDto.setBookingTime(bookingTime);
        return bookingPriceInfoDto;
    }
}
