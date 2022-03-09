/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.DtoList;

import lombok.Getter;
import lombok.Setter;

/**
 * The event show dto list.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
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
    public static EventShowDtoList of(final List<EventShowDto> eventShowDtos) {
        if (CollectionUtils.isEmpty(eventShowDtos)) {
            return new EventShowDtoList();
        }
        return new EventShowDtoList(eventShowDtos);
    }
}
