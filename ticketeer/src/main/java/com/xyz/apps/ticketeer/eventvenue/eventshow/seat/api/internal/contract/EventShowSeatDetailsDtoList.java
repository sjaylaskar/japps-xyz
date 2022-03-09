/*
* Id: EventShowSeatDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * The event show seat details dto list.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatDetailsDtoList {

    /** The event show id. */
    private Long eventShowId;

    /** The auditorium id. */
    private Long auditoriumId;

    /** The auditorium name. */
    private String auditoriumName;

    /** The event show details dtos. */
    private List<EventShowSeatDetailsDto> eventShowDetailsDtos = new ArrayList<>();

    /**
     * Instantiates a new event show seat details dto list.
     */
    public EventShowSeatDetailsDtoList() {

    }

    /**
     * Instantiates a new event show seat details dto list.
     *
     * @param eventShowSeatDetailsDtos the event show seat details dtos
     */
    public EventShowSeatDetailsDtoList(final List<EventShowSeatDetailsDto> eventShowSeatDetailsDtos) {
        eventShowDetailsDtos.addAll(eventShowSeatDetailsDtos);
    }

    /**
     * Of.
     *
     * @param eventShowId the event show id
     * @param auditoriumId the auditorium id
     * @param auditoriumName the auditorium name
     * @param eventShowSeatDetailsDtos the event show seat details dtos
     * @return the event show seat details dto list
     */
    public static EventShowSeatDetailsDtoList of(final Long eventShowId, final Long auditoriumId, final String auditoriumName, final List<EventShowSeatDetailsDto> eventShowSeatDetailsDtos) {
        final EventShowSeatDetailsDtoList eventShowSeatDetailsDtoList = new EventShowSeatDetailsDtoList(eventShowSeatDetailsDtos);
        eventShowSeatDetailsDtoList.setEventShowId(eventShowId);
        eventShowSeatDetailsDtoList.setAuditoriumId(auditoriumId);
        eventShowSeatDetailsDtoList.setAuditoriumName(auditoriumName);
        return eventShowSeatDetailsDtoList;
    }
}
