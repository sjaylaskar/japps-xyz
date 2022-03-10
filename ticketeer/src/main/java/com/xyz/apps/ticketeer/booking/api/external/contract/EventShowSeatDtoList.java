/*
* Id: EventShowSeatDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external.contract;

import java.util.ArrayList;
import java.util.List;

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
public class EventShowSeatDtoList {

    private List<EventShowSeatDto> dtos = new ArrayList<>();

    /**
     * Instantiates a new event show seat dto list.
     */
    public EventShowSeatDtoList() {

    }

    /**
     * Instantiates a new event show seat dto list.
     *
     * @param dtos the event show seat minimal dtos
     */
    public EventShowSeatDtoList(final List<EventShowSeatDto> dtos) {
        this.dtos.addAll(dtos);
    }
}
