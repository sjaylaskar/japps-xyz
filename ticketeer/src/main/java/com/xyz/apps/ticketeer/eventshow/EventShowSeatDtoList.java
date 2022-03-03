/*
* Id: EventShowSeatDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.model.DtoList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show seat dto list.
 */
@Getter
@Setter
@ToString
public class EventShowSeatDtoList extends DtoList<EventShowSeatDto> {

    /**
     * Instantiates a new event show seat dto list.
     */
    EventShowSeatDtoList() {

    }

    /**
     * Instantiates a new event show seat dto list.
     *
     * @param eventShowSeatDtos the event show seat dtos
     */
    EventShowSeatDtoList(final List<EventShowSeatDto> eventShowSeatDtos) {
        super(eventShowSeatDtos);
    }

    /**
     * Of.
     *
     * @param eventShowDtos the event show seat dtos
     * @return the event show seat dto list
     */
    static EventShowSeatDtoList of(final List<EventShowSeatDto> eventShowSeatDtos) {
        if (CollectionUtils.isEmpty(eventShowSeatDtos)) {
            return new EventShowSeatDtoList();
        }
        return new EventShowSeatDtoList(eventShowSeatDtos);
    }
}
