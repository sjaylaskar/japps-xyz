/*
* Id: EventShowSeatsBookingDto.java 06-Mar-2022 5:21:37 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external.contract;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show seats booking dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventShowSeatsBookingDto {

    /** The seat ids. */
    private Set<Long> seatIds = new HashSet<>();

    /** The booking id. */
    private Long bookingId;
}
