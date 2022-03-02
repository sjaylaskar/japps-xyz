/*
* Id: EventShowSeat.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.xyz.apps.ticketeer.booking.Booking;
import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show seat.
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
    @Column(nullable = false)
    @Min(value = 1, message = "Seat price must be atleast 1.")
    @NotNull(message = "Seat price cannot be null.")
    private Double amount;

    /** The seat status. */
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "default 0")
    @NotNull(message = "Seat reservation status cannot be null.")
    private SeatReservationStatus seatReservationStatus;

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
    @Column(nullable = true)
    private Booking booking;

    /**
     * Instantiates a new event show seat.
     */
    public EventShowSeat() {

    }

    /**
     * Instantiates a new event show seat.
     *
     * @param amount the amount
     * @param seatReservationStatus the seat status
     * @param eventShow the event show
     * @param auditoriumSeat the auditorium seat
     * @param booking the booking
     */
    public EventShowSeat(
    @Min(value = 1, message = "Seat price must be atleast 1.")
    @NotNull(message = "Seat price cannot be null.")
    final Double amount,
    @NotNull(message = "Seat status cannot be null.")
    final SeatReservationStatus seatReservationStatus,
    final EventShow eventShow,
    final AuditoriumSeat auditoriumSeat,
    final Booking booking) {

        this.amount = amount;
        this.seatReservationStatus = seatReservationStatus;
        this.eventShow = eventShow;
        this.auditoriumSeat = auditoriumSeat;
        this.booking = booking;
    }
}
