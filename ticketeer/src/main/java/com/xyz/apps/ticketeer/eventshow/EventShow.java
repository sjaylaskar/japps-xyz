/*
* Id: Show.java 15-Feb-2022 3:06:09 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.xyz.apps.ticketeer.event.Event;
import com.xyz.apps.ticketeer.eventvenue.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.EventVenue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class EventShow extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The date. */
    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "Show date cannot be null.")
    private LocalDate date;

    /** The start time. */
    @Column(nullable = false, columnDefinition = "TIME")
    @NotNull(message = "Show start time cannot be null.")
    private LocalTime startTime;

    /** The end time. */
    @Column(nullable = false, columnDefinition = "TIME")
    @NotNull(message = "Show end time cannot be null.")
    private LocalTime endTime;

    /** The event venue. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventVenueId", nullable = false, updatable = false)
    private EventVenue eventVenue;

    /** The auditorium. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoriumId", nullable = false, updatable = false)
    private Auditorium auditorium;

    /** The event. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventId", nullable = false, updatable = false)
    private Event event;

    /**
     * Instantiates a new event show.
     */
    public EventShow() {

    }

    /**
     * Instantiates a new event show.
     *
     * @param date the date
     * @param startTime the start time
     * @param endTime the end time
     * @param event the event
     * @param eventVenue the event venue
     * @param auditorium the auditorium
     */
    public EventShow(
    @NotNull(message = "Show date cannot be null.")
    final LocalDate date,
    @NotNull(message = "Show start time cannot be null.")
    final LocalTime startTime,
    @NotNull(message = "Show end time cannot be null.")
    final LocalTime endTime,
    final Event event,
    final EventVenue eventVenue,
    final Auditorium auditorium) {

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.event = event;
        this.eventVenue = eventVenue;
        this.auditorium = auditorium;
    }


}
