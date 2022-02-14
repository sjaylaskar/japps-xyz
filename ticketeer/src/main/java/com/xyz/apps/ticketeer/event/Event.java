/*
* Id: Event.java 15-Feb-2022 3:00:51 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.xyz.apps.ticketeer.model.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The Event.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@javax.persistence.Entity
@Getter
@Setter
@ToString
public class Event extends Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /** The name. */
    private String name;
}
