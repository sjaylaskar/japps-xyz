/*
* Id: Event.java 15-Feb-2022 3:00:51 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import com.xyz.apps.ticketeer.model.general.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event venue dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventVenueDto extends Dto<EventVenue> {

    /** The name. */
    private String name;

    /** The number of auditoriums. */
    private int numberOfAuditoriums;

    /** The city. */
    private Long cityId;
}
