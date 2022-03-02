/*
* Id: EventShowSeat.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_show_seq")
    private Long id;

    /** The amount. */
    @Column(nullable = false)
    @Min(value = 1, message = "Seat price must be atleast 1.")
    @NotNull(message = "Seat price cannot be null.")
    private Double amount;

    /** The seat status. */
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    @NotNull(message = "Seat reservation status cannot be null.")
    private SeatReservationStatus seatReservationStatus = SeatReservationStatus.AVAILABLE;

    /** The event show. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventShowId", nullable = false)
    private EventShow eventShow;

    /** The auditorium seat. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoriumSeatId", nullable = false)
    private AuditoriumSeat auditoriumSeat;

    /** The reservation time. */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime reservationTime;

    /** The booking. */
    @ManyToOne
    @JoinColumn(name = "bookingId")
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
    @NotNull(message = "Seat reservation status cannot be null.")
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
