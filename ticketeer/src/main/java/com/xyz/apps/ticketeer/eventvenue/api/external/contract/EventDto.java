/*
* Id: Event.java 15-Feb-2022 3:00:51 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.external.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    /** The id. */
    private Long id;

    /** The name. */
    private String name;

    /**
     * Of.
     *
     * @param eventDetailsDto the event details dto
     * @return the event dto
     */
    public static EventDto of(final EventDetailsDto eventDetailsDto) {
        final EventDto eventDto = new EventDto();
        eventDto.setId(eventDetailsDto.getEventId());
        eventDto.setName(eventDetailsDto.getName());
        return eventDto;
    }
}
