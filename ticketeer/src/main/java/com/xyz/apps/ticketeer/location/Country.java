/*
 * Id: Country.java 13-Feb-2022 5:33:12 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.xyz.apps.ticketeer.model.AbstractEntity;

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
public class Country extends AbstractEntity {

    /** The code. */
    @Column(unique = true, nullable = false)
    private String code;

    /** The name. */
    @Column(unique = true, nullable = false)
    private String name;
}
