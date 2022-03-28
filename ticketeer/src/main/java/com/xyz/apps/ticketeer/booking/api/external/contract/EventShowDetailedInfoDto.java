/*
* Id: EventShowDetailedInfoDto.java 02-Mar-2022 5:45:41 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show detailed info dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowDetailedInfoDto {

    /** The id. */
    private Long eventShowId;

    /** The date. */
    private String date;

    /** The start time. */
    private String startTime;

    /** The end date. */
    private String endDate;

    /** The end time. */
    private String endTime;

    /** The event. */
    private String eventName;

    /** The city name. */
    private String cityName;

    /** The event venue. */
    private String eventVenueName;

    /** The auditorium. */
    private String auditoriumName;
}
