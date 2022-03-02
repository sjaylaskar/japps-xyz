/*
* Id: EventVenueDetailsDto.java 02-Mar-2022 12:04:24 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import com.xyz.apps.ticketeer.location.City;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event venue details dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventVenueDetailsDto {

    /** The event venue name. */
    private String name;

    /** The event venue city. */
    private City city;

    /** The number of auditoriums. */
    private int numberOfAuditoriums;

    /** The auditorium details list. */
    private final AuditoriumDetailsDtoList auditoriumDetailsDtoList = new AuditoriumDetailsDtoList();

}
