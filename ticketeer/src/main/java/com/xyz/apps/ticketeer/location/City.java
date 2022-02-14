/*
 * Id: City.java 13-Feb-2022 5:34:55 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.xyz.apps.ticketeer.model.AbstractEntity;

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
public class City extends AbstractEntity {

    /** The code. */
    @Column(unique = true, nullable = false)
    private String code;

    /** The name. */
    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id", nullable = false, updatable = false)
    private Country country;
}
