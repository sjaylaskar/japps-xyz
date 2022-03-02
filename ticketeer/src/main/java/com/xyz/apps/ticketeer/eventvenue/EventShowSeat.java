/*
* Id: EventShowSeat.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.xyz.apps.ticketeer.booking.Booking;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The EventShowSeat.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class EventShowSeat extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The amount. */
    private Double amount;

    /** The seat status. */
    private SeatStatus seatStatus;

    /** The event show. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventShowId", nullable = false, updatable = false)
    private EventShow eventShow;

    /** The auditorium seat. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoriumSeatId", nullable = false, updatable = false)
    private AuditoriumSeat auditoriumSeat;

    /** The booking. */
    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
}
