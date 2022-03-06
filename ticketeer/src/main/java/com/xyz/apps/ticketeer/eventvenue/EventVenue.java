/*
* Id: Theatre.java 13-Feb-2022 4:30:24 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event venue.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class EventVenue extends com.xyz.apps.ticketeer.model.general.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_venue_seq")
    private Long id;

    /** The name. */
    @Column(nullable = false)
    private String name;

    /** The number of auditoriums. */
    @Column(nullable = false)
    @NotNull(message = "The number of auditoriums cannot be null.")
    @Min(value = 1, message = "The number of auditoriums must be atleast 1.")
    private Integer numberOfAuditoriums;

    /** The city. */
    @Column(nullable = false)
    @NotNull(message = "The city id cannot be null.")
    private Long cityId;

    public EventVenue id(final Long id) {
        this.id = id;
        return this;
    }
}
