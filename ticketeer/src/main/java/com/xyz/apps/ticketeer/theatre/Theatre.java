/*
* Id: Theatre.java 13-Feb-2022 4:30:24 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.theatre;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.xyz.apps.ticketeer.location.City;
import com.xyz.apps.ticketeer.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The theatre.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class Theatre extends AbstractEntity {

    /** The name. */
    private String name;

    /** The city. */
    private City city;

    /**
     * Gets the city.
     *
     * @return the city
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "id", nullable = false, updatable = true)
    public City getCity() {

        return city;
    }
}
