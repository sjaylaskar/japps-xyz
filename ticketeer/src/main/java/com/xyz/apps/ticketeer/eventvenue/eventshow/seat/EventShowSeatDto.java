/*
* Id: EventShowSeat.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import com.xyz.apps.ticketeer.general.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show seat.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventShowSeatDto extends Dto<EventShowSeat> {

    /** The id. */
    private Long id;

    /** The amount. */
    private Double amount;

    /** The seat reservation status. */
    private String seatReservationStatus = SeatReservationStatus.AVAILABLE.name();

    /** The event show. */
    private Long eventShowId;

    /** The auditorium seat. */
    private Long auditoriumSeatId;

    /** The reservation time. */
    private String reservationTime;

    /** The booking. */
    private Long bookingId;
}
