/*
* Id: Show.java 15-Feb-2022 3:06:09 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.xyz.apps.ticketeer.event.Event;

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
    private Date date;

    /** The start time. */
    private Date startTime;

    /** The end time. */
    private Date endTime;

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
}
