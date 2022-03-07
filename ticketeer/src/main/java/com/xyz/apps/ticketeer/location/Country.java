/*
 * Id: Country.java 13-Feb-2022 5:33:12 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The country.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class Country extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq")
    private Long id;

    /** The code. */
    @Column(unique = true, nullable = false)
    @NotNull(message = "Country code is required.")
    private String code;

    /** The name. */
    @Column(unique = true, nullable = false)
    @NotNull(message = "Country name is required.")
    private String name;

    /**
     * Id.
     *
     * @param id the id
     * @return the country
     */
    public Country id(final Long id) {
        this.id = id;
        return this;
    }
}
