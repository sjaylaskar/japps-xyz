/*
* Id: Theatre.java 13-Feb-2022 4:30:24 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.theatre;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /** The name. */
    private String name;

    /** The city. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false, updatable = false)
    private City city;

}
