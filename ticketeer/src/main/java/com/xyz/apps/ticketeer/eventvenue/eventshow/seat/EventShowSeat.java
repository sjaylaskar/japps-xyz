/*
* Id: EventShowSeat.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeat;
import com.xyz.apps.ticketeer.eventvenue.eventshow.EventShow;

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
@Validated
public class EventShowSeat extends com.xyz.apps.ticketeer.model.general.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_show_seq")
    private Long id;

    /** The amount. */
    @Column(nullable = false)
    @NotNull(message = "Seat price cannot be null.")
    private Double amount;

    /** The seat status. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Seat reservation status cannot be null.")
    private SeatReservationStatus seatReservationStatus = SeatReservationStatus.AVAILABLE;

    /** The event show. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventShowId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EventShow eventShow;

    /** The auditorium seat. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoriumSeatId", nullable = false)
    private AuditoriumSeat auditoriumSeat;

    /** The reservation time. */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime reservationTime;

    /** The booking. */
    private Long bookingId;

    /**
     * Instantiates a new event show seat.
     */
    public EventShowSeat() {

    }

    /**
     * Instantiates a new event show seat.
     *
     * @param amount the amount
     * @param seatReservationStatus the seat reservation status
     * @param eventShow the event show
     * @param auditoriumSeat the auditorium seat
     * @param reservationTime the reservation time
     * @param bookingId the booking id
     */
    public EventShowSeat(@NotNull(message = "Seat price cannot be null.") final Double amount, @NotNull(
        message = "Seat reservation status cannot be null."
    ) final SeatReservationStatus seatReservationStatus, final EventShow eventShow, final AuditoriumSeat auditoriumSeat,
            final LocalDateTime reservationTime, final Long bookingId) {

        this.amount = amount;
        this.seatReservationStatus = seatReservationStatus;
        this.eventShow = eventShow;
        this.auditoriumSeat = auditoriumSeat;
        this.reservationTime = reservationTime;
        this.bookingId = bookingId;
    }
}
