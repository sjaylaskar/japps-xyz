/*
 * Id: BookingDto.java 03-Mar-2022 1:04:31 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The booking dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Builder
public class BookingDto {

    /** The booking id. */
    private Long bookingId;

    /** The event show seat ids. */
    @Builder.Default
    private List<Long> eventShowSeatIds = new ArrayList<>();

    /** The booking time. */
    private String bookingTime;

    /** The reservation time. */
    private String reservationTime;

    /** The booking status. */
    private String bookingStatus;

    /** The amount. */
    private Double amount;

    /** The final amount. */
    private Double finalAmount;

    /** The offer code. */
    private String offerCode;

    /** The user. */
    private Long userId;

    /** The event show. */
    private Long eventShowId;

    /** The is reserved. */
    private boolean isReserved;

    /** The is confirmed. */
    private boolean isConfirmed;

    /**
     * Instantiates a new booking dto.
     */
    public BookingDto() {

    }

    /**
     * Instantiates a new booking dto.
     *
     * @param bookingId the booking id
     * @param eventShowSeatIds the event show seat ids
     * @param bookingTime the booking time
     * @param reservationTime the reservation time
     * @param bookingStatus the booking status
     * @param amount the amount
     * @param finalAmount the final amount
     * @param offerCode the offer code
     * @param userId the user id
     * @param eventShowId the event show id
     * @param isReserved the is reserved
     * @param isConfirmed the is confirmed
     */
    BookingDto(final Long bookingId,
                      final List<Long> eventShowSeatIds,
                      final String bookingTime,
                      final String reservationTime,
                      final String bookingStatus,
                      final Double amount,
                      final Double finalAmount,
                      final String offerCode,
                      final Long userId,
                      final Long eventShowId,
                      final boolean isReserved,
                      final boolean isConfirmed) {

        this.bookingId = bookingId;
        this.eventShowSeatIds = eventShowSeatIds;
        this.bookingTime = bookingTime;
        this.reservationTime = reservationTime;
        this.bookingStatus = bookingStatus;
        this.amount = amount;
        this.finalAmount = finalAmount;
        this.offerCode = offerCode;
        this.userId = userId;
        this.eventShowId = eventShowId;
        this.isReserved = isReserved;
        this.isConfirmed = isConfirmed;
    }
}
