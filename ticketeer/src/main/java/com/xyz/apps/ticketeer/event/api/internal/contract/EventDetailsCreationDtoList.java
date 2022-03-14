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
 * The event details creation dto list.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDetailsCreationDtoList extends DtoList<EventDetailsCreationDto> {

    /**
     * Instantiates a new event details creation dto list.
     */
    EventDetailsCreationDtoList() {

    }

    /**
     * Instantiates a new event details creation dto list.
     *
     * @param eventDetailsCreationDtos the event details creation dtos
     */
    EventDetailsCreationDtoList(final List<EventDetailsCreationDto> eventDetailsCreationDtos) {

        super(eventDetailsCreationDtos);
    }

    /**
     * Of.
     *
     * @param eventDetailsCreationDtos the event details creation dtos
     * @return the event details creation dto list
     */
    public static EventDetailsCreationDtoList of(final List<EventDetailsCreationDto> eventDetailsCreationDtos) {

        if (CollectionUtils.isEmpty(eventDetailsCreationDtos)) {
            return new EventDetailsCreationDtoList();
        }
        return new EventDetailsCreationDtoList(eventDetailsCreationDtos);
    }
}
