/*
 * Id: Booking.java 15-Feb-2022 3:14:35 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.api.internal.contract;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The booking details.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDetailsDto {

    /** The id. */
    private Long bookingId;

    /** The event show name. */
    private String eventName;

    /** The event show date. */
    private String eventShowDate;

    /** The event show time. */
    private String eventShowTime;

    /** The event venue name. */
    private String eventVenueName;

    /** The auditorium name. */
    private String auditoriumName;

    /** The city name. */
    private String cityName;

    /** The seats. */
    private List<String> seatNumbers;

    /** The booking time. */
    private String bookingTime;

    /** The booking status. */
    private String bookingStatus;

    /** The amount. */
    private Double amount;

    /** The offer code. */
    private String offerCode;

    /** The user. */
    private String username;

    /** The phone number. */
    private String phoneNumber;

    /** The email id. */
    private String emailId;
}
