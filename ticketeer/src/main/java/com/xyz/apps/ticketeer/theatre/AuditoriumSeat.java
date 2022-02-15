/*
* Id: AuditoriumSeat.java 15-Feb-2022 3:16:29 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.theatre;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.xyz.apps.ticketeer.model.Entity;


/**
 * The AuditoriumSeat.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@javax.persistence.Entity
public class AuditoriumSeat extends Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
}
