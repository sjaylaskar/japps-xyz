/*
* Id: EventShowDetailsDto.java 02-Mar-2022 3:06:56 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.api.external.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show details dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowDetailsDto {

    /** The id. */
    private Long id;

    /** The date. */
    private String date;

    /** The start time. */
    private String startTime;

    /** The end date. */
    private String endDate;

    /** The end time. */
    private String endTime;

    /** The event. */
    private Long eventId;

    /** The city id. */
    private Long cityId;

    /** The event venue id. */
    private Long eventVenueId;

    /** The auditorium. */
    private String auditoriumName;

}
