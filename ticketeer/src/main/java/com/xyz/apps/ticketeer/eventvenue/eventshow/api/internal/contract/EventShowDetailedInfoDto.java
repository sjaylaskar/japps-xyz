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

    /**
     * Of.
     *
     * @param eventShowDto the event show dto
     * @param eventName the event name
     * @param cityName the city name
     * @param eventVenueName the event venue name
     * @param auditoriumName the auditorium name
     * @return the event show detailed info dto
     */
    public static EventShowDetailedInfoDto of(final EventShowDto eventShowDto, final String eventName, final String cityName, final String eventVenueName, final String auditoriumName) {
        final EventShowDetailedInfoDto eventShowDetailedInfoDto = new EventShowDetailedInfoDto();
        eventShowDetailedInfoDto.setEventShowId(eventShowDto.getId());
        eventShowDetailedInfoDto.setDate(eventShowDto.getDate());
        eventShowDetailedInfoDto.setStartTime(eventShowDto.getStartTime());
        eventShowDetailedInfoDto.setEndTime(eventShowDto.getEndTime());
        eventShowDetailedInfoDto.setEventName(eventName);
        eventShowDetailedInfoDto.setCityName(cityName);
        eventShowDetailedInfoDto.setEventVenueName(eventVenueName);
        eventShowDetailedInfoDto.setAuditoriumName(auditoriumName);
        return eventShowDetailedInfoDto;
    }

}
