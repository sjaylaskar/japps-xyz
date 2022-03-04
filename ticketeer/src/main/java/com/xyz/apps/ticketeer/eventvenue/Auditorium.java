/*
* Id: Auditorium.java 15-Feb-2022 3:04:24 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Auditorium.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class Auditorium extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_seq")
    private Long id;

    /** The name. */
    @Column(unique = true, nullable = false)
    @NotNull(message = "Auditorium name is required.")
    private String name;

    /** The number of seats. */
    @Column(nullable = false)
    @NotNull(message = "The number of seats cannot be null.")
    @Min(value = 1, message = "Number of seats must be atleast 1.")
    private Integer numberOfSeats = 1;

    /** The event venue. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventVenueId", nullable = false)
    private EventVenue eventVenue;

    /**
     * Instantiates a new auditorium.
     *
     * @param name the name
     * @param numberOfSeats the number of seats
     * @param eventVenue the event venue
     */
    public Auditorium(final String name, final Integer numberOfSeats, final EventVenue eventVenue) {

        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.eventVenue = eventVenue;
    }

    /**
     * Instantiates a new auditorium.
     */
    public Auditorium() {

    }
}
