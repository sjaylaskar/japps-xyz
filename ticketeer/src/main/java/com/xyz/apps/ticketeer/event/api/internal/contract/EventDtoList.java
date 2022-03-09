/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.api.internal.contract;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.DtoList;

import lombok.Getter;
import lombok.Setter;

/**
 * The event dto list.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDtoList extends DtoList<EventDto> {

    /**
     * Instantiates a new event dto list.
     */
    EventDtoList() {

    }

    /**
     * Instantiates a new event dto list.
     *
     * @param eventDtos the event dtos
     */
    EventDtoList(final List<EventDto> eventDtos) {
        super(eventDtos);
    }

    /**
     * Of.
     *
     * @param eventDtos the event dtos
     * @return the event dto list
     */
    static EventDtoList of(final List<EventDto> eventDtos) {
        if (CollectionUtils.isEmpty(eventDtos)) {
            return new EventDtoList();
        }
        return new EventDtoList(eventDtos);
    }
}
