/*
 * Id: BookingDto.java 03-Mar-2022 1:04:31 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

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
public class BookingDto {

    /** The booking id. */
    private Long bookingId;

    /** The event show seat ids. */
    private Set<Long> eventShowSeatIds = new HashSet<>();

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

    /** The event venue id. */
    private Long eventVenueId;

    /** The event show id. */
    private Long eventShowId;

    /** The city id. */
    private Long cityId;

    /** The show start time. */
    private LocalTime showStartTime;

    /** The user. */
    private String username;

    /** The password. */
    private String password;

    /** The phone number. */
    private String phoneNumber;

    /** The email id. */
    private String emailId;

    /** The is reserved. */
    private boolean isReserved;

    /** The is confirmed. */
    private boolean isConfirmed;

}
