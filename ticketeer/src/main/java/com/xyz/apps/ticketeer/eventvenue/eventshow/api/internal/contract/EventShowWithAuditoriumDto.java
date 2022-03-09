/*
* Id: EventShowWithAuditoriumDto.java 09-Mar-2022 4:15:54 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show with auditorium dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowWithAuditoriumDto {

    /** The event show id. */
    private Long eventShowId;

    /** The auditorium id. */
    private Long auditoriumId;

    /**
     * Of.
     *
     * @param eventShowId the event show id
     * @param auditoriumId the auditorium id
     * @return the event show with auditorium dto
     */
    public static EventShowWithAuditoriumDto of(final Long eventShowId, final Long auditoriumId) {
        final EventShowWithAuditoriumDto eventShowWithAuditoriumDto = new EventShowWithAuditoriumDto();
        eventShowWithAuditoriumDto.setEventShowId(eventShowId);
        eventShowWithAuditoriumDto.setAuditoriumId(auditoriumId);
        return eventShowWithAuditoriumDto;
    }
}
