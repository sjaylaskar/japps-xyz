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
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

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
@Validated
public class EventVenue extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_venue_seq")
    @SequenceGenerator(initialValue = 1, name = "event_venue_seq", allocationSize = 1)
    private Long id;

    /** The name. */
    @Column(nullable = false)
    @NotEmpty(message = "The event venue name is mandatory.")
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
