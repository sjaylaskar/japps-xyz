/*
* Id: EventShowDetailsDto.java 02-Mar-2022 3:06:56 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The EventShowDetailsDto.
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

    /**
     * Of.
     *
     * @param eventShowDto the event show dto
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the event show details dto
     */
    public static EventShowDetailsDto of(final EventShowDto eventShowDto, final Long eventVenueId, final String auditoriumName) {
        if (eventShowDto != null && eventVenueId != null && StringUtils.isNotBlank(auditoriumName)) {
            final EventShowDetailsDto eventShowDetailsDto = new EventShowDetailsDto();
            eventShowDetailsDto.setId(eventShowDto.getId());
            eventShowDetailsDto.setDate(eventShowDto.getDate());
            eventShowDetailsDto.setStartTime(eventShowDto.getStartTime());
            eventShowDetailsDto.setEndDate(eventShowDto.getEndDate());
            eventShowDetailsDto.setEndTime(eventShowDto.getEndTime());
            eventShowDetailsDto.setEventId(eventShowDto.getEventId());
            eventShowDetailsDto.setCityId(eventShowDto.getCityId());
            eventShowDetailsDto.setEventVenueId(eventVenueId);
            eventShowDetailsDto.setAuditoriumName(auditoriumName);
            return eventShowDetailsDto;
        }
        return null;
    }
}
