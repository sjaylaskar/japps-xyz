/*
 * Id: City.java 13-Feb-2022 5:34:55 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The city.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class City extends com.xyz.apps.ticketeer.model.Entity {

    /** The city id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The code. */
    @Column(unique = true, nullable = false)
    @NotNull(message = "City code is required.")
    private String code;

    /** The name. */
    @Column(nullable = false)
    @NotNull(message = "City name is required.")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "countryId", nullable = false, updatable = false)
    @NotNull(message = "Country is required for City.")
    private Country country;
}
