/*
* Id: EventVenueDetailsDto.java 02-Mar-2022 12:04:24 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventVenueDetailsDto {

    /** The event venue name. */
    private String name;

    /** The event venue city. */
    private Long cityId;

    /** The number of auditoriums. */
    private int numberOfAuditoriums;

    /** The auditorium details list. */
    private final AuditoriumDetailsDtoList auditoriumDetailsDtoList = new AuditoriumDetailsDtoList();

}