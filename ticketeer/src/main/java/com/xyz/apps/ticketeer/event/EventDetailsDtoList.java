/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.general.model.DtoList;

import lombok.Getter;
import lombok.Setter;

/**
 * The event details dto list.
 */
@Getter
@Setter
public class EventDetailsDtoList extends DtoList<EventDetailsDto> {

    /**
     * Instantiates a new event details dto list.
     */
    EventDetailsDtoList() {

    }

    /**
     * Instantiates a new event details dto list.
     *
     * @param eventDetailsDtos the event details dtos
     */
    EventDetailsDtoList(final List<EventDetailsDto> eventDetailsDtos) {
        super(eventDetailsDtos);
    }

    /**
     * Of.
     *
     * @param eventDetailsDtos the event details dtos
     * @return the event details dto list
     */
    static EventDetailsDtoList of(final List<EventDetailsDto> eventDetailsDtos) {
        if (CollectionUtils.isEmpty(eventDetailsDtos)) {
            return new EventDetailsDtoList();
        }
        return new EventDetailsDtoList(eventDetailsDtos);
    }
}
