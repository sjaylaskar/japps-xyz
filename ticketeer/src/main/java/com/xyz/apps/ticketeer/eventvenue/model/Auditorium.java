/*
* Id: Auditorium.java 15-Feb-2022 3:04:24 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

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
@Validated
public class Auditorium extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_seq")
    @SequenceGenerator(initialValue = 1, name = "auditorium_seq", allocationSize = 1)
    private Long id;

    /** The name. */
    @Column(nullable = false)
    @NotBlank(message = "Auditorium name is required.")
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

    public Auditorium id(final Long id) {
        this.id = id;
        return this;
    }
}