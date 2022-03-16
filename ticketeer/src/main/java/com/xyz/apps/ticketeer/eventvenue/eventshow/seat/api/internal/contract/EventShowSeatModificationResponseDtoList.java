/*
* Id: EventShowSeatModificationResponseDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.DtoList;

import lombok.Getter;
import lombok.Setter;

/**
 * The event show seat modification response dto list.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatModificationResponseDtoList extends DtoList<EventShowSeatModificationResponseDto> {

    /**
     * Instantiates a new event show seat dto list.
     */
    EventShowSeatModificationResponseDtoList() {

    }

    /**
     * Instantiates a new event show seat dto list.
     *
     * @param eventShowSeatModificationResponseDtos the event show seat dtos
     */
    EventShowSeatModificationResponseDtoList(final List<EventShowSeatModificationResponseDto> eventShowSeatModificationResponseDtos) {
        super(eventShowSeatModificationResponseDtos);
    }

    /**
     * Of.
     *
     * @param eventShowDtos the event show seat dtos
     * @return the event show seat dto list
     */
    public static EventShowSeatModificationResponseDtoList of(final List<EventShowSeatModificationResponseDto> eventShowSeatModificationResponseDtos) {
        if (CollectionUtils.isEmpty(eventShowSeatModificationResponseDtos)) {
            return new EventShowSeatModificationResponseDtoList();
        }
        return new EventShowSeatModificationResponseDtoList(eventShowSeatModificationResponseDtos);
    }
}
