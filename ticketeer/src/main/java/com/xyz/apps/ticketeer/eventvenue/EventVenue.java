/*
* Id: Theatre.java 13-Feb-2022 4:30:24 pm SubhajoyLaskar
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

import com.xyz.apps.ticketeer.location.City;

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
public class EventVenue extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The name. */
    private String name;

    /** The number of auditoriums. */
    private int numberOfAuditoriums;

    /** The city. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cityId", nullable = false, updatable = false)
    private City city;

    /**
     * Instantiates a new event venue.
     *
     * @param name the name
     * @param numberOfAuditoriums the number of auditoriums 
     * @param city the city
     */
    public EventVenue(final String name, final int numberOfAuditoriums, final City city) {
        this.name = name;
        this.numberOfAuditoriums = numberOfAuditoriums;
        this.city = city;
    }
}
