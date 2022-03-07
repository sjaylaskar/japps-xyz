/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.general.model.DtoList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show dto list.
 */
@Getter
@Setter
@ToString
public class EventShowDtoList extends DtoList<EventShowDto> {

    /**
     * Instantiates a new event show dto list.
     */
    EventShowDtoList() {

    }

    /**
     * Instantiates a new event show dto list.
     *
     * @param eventShowDtos the event show dtos
     */
    EventShowDtoList(final List<EventShowDto> eventShowDtos) {
        super(eventShowDtos);
    }

    /**
     * Of.
     *
     * @param eventShowDtos the event show dtos
     * @return the event show dto list
     */
    static EventShowDtoList of(final List<EventShowDto> eventShowDtos) {
        if (CollectionUtils.isEmpty(eventShowDtos)) {
            return new EventShowDtoList();
        }
        return new EventShowDtoList(eventShowDtos);
    }
}
