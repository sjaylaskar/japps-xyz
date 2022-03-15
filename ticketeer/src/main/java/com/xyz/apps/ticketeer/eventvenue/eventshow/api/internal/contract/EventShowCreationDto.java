/*
* Id: EventShowDto.java 02-Mar-2022 5:45:41 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowCreationDto {

    /** The date. */
    private String date;

    /** The start time. */
    private String startTime;

    /** The event. */
    private Long eventId;

    /** The city id. */
    private Long cityId;

    /** The event venue id. */
    private Long eventVenueId;

    /** The auditorium. */
    private String auditoriumName;

}
