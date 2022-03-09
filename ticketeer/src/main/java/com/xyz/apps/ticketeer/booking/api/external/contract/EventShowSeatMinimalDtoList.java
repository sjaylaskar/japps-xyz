/*
* Id: EventShowSeatDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external.contract;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show seat minimal dto list.
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatMinimalDtoList {

    private List<EventShowSeatMinimalDto> eventShowSeatMinimalDtos = new ArrayList<>();

    /**
     * Instantiates a new event show seat dto list.
     */
    EventShowSeatMinimalDtoList() {

    }

    /**
     * Instantiates a new event show seat dto list.
     *
     * @param eventShowSeatMinimalDtos the event show seat minimal dtos
     */
    EventShowSeatMinimalDtoList(final List<EventShowSeatMinimalDto> eventShowSeatMinimalDtos) {
        this.eventShowSeatMinimalDtos = eventShowSeatMinimalDtos;
    }

    /**
     * Of.
     *
     * @param eventShowSeatMinimalDtos the event show seat minimal dtos
     * @return the event show seat dto list
     */
    public static EventShowSeatMinimalDtoList of(final List<EventShowSeatMinimalDto> eventShowSeatMinimalDtos) {
        if (CollectionUtils.isEmpty(eventShowSeatMinimalDtos)) {
            return new EventShowSeatMinimalDtoList();
        }
        return new EventShowSeatMinimalDtoList(eventShowSeatMinimalDtos);
    }
}
