/*
* Id: EventShowDto.java 02-Mar-2022 5:45:41 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.api.external.contract;

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
public class EventShowDto {

    /** The date. */
    private String date;

    /** The start time. */
    private String startTime;

    /** The end time. */
    private String endTime;

    /** The city id. */
    private Long cityId;

    /** The event venue. */
    private Long eventVenueId;

    /** The auditorium. */
    private Long auditoriumId;

    /** The event. */
    private Long eventId;
}