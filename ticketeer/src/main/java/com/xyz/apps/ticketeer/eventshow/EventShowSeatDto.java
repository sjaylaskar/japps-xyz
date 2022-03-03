/*
* Id: EventShowSeat.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import java.time.LocalDateTime;

import com.xyz.apps.ticketeer.booking.Booking;
import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeat;
import com.xyz.apps.ticketeer.model.Dto;

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

    /** The amount. */
    private Double amount;

    /** The seat reservation status. */
    private SeatReservationStatus seatReservationStatus = SeatReservationStatus.AVAILABLE;

    /** The event show. */
    private EventShow eventShow;

    /** The auditorium seat. */
    private AuditoriumSeat auditoriumSeat;

    /** The reservation time. */
    private LocalDateTime reservationTime;

    /** The booking. */
    private Booking booking;
}
