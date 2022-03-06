/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.api.external.contract;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show dto list.
 */
@Getter
@Setter
@ToString
public class EventShowDtoList {

    private List<EventShowDto> dtos = new ArrayList<>();

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
    EventShowDtoList(final List<EventShowDto> dtos) {
        this.dtos.addAll(dtos);
    }
}
