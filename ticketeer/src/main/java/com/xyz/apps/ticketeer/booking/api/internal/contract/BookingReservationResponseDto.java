/*
 * Id: BookingReservationResponseDto.java 03-Mar-2022 1:04:31 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.api.internal.contract;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The booking reservation response dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingReservationResponseDto {

    /** The booking id. */
    private Long bookingId;

    /** The booking reservation id. */
    private String bookingReservationId;

    /** The reservation time. */
    private String reservationTime;

    /** The booking status. */
    private String bookingStatus;

    /** The user. */
    private String username;

    /** The phone number. */
    private String phoneNumber;

    /** The email id. */
    private String emailId;

    /** The event show id. */
    private Long eventShowId;

    /** The event show seat ids. */
    private Set<String> seatNumbers = new HashSet<>();
}
