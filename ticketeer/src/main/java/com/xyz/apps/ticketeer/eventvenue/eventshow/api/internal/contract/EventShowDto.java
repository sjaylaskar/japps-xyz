/*
 * Id: EventShowDto.java 02-Mar-2022 5:45:41 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShow;
import com.xyz.apps.ticketeer.general.model.Dto;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;

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
public class EventShowDto extends Dto<EventShow> {

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

    /** The auditorium. */
    private Long auditoriumId;

    /**
     * Of.
     *
     * @param eventShowCreationDto the event show creation dto
     * @param auditoriumId the auditorium id
     * @param eventShowEndDateTime the event show end date time
     * @return the event show dto
     */
    public static EventShowDto of(final EventShowCreationDto eventShowCreationDto, final Long auditoriumId,
            final LocalDateTime eventShowEndDateTime) {

        if (eventShowCreationDto != null && auditoriumId != null && eventShowEndDateTime != null) {
            final EventShowDto eventShowDto = new EventShowDto();
            eventShowDto.setDate(eventShowCreationDto.getDate());
            eventShowDto.setStartTime(eventShowCreationDto.getStartTime());
            eventShowDto.setEndDate(LocalDateTimeFormatUtil.format(eventShowEndDateTime.toLocalDate()));
            eventShowDto.setEndTime(LocalDateTimeFormatUtil.format(eventShowEndDateTime.toLocalTime()));
            eventShowDto.setEventId(eventShowCreationDto.getEventId());
            eventShowDto.setCityId(eventShowCreationDto.getCityId());
            eventShowDto.setAuditoriumId(auditoriumId);
            return eventShowDto;
        }
        return null;
    }

}
