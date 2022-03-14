/*
* Id: EventVenueDtoList.java 15-Mar-2022 3:47:42 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.general.model.DtoList;


/**
 * The event venue dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventVenueDtoList extends DtoList<EventVenueDto> {

    /**
     * Instantiates a new event venue dto list.
     */
    public EventVenueDtoList() {

    }

    /**
     * Instantiates a new event venue dto list.
     *
     * @param eventVenueDtos the event venue dtos
     */
    public EventVenueDtoList(final List<EventVenueDto> eventVenueDtos) {
        super(eventVenueDtos);
    }

    /**
     * Of.
     *
     * @param eventVenueDtos the event venue dtos
     * @return the event venue dto list
     */
    public static EventVenueDtoList of(final List<EventVenueDto> eventVenueDtos) {
        if (CollectionUtils.isEmpty(eventVenueDtos)) {
            return new EventVenueDtoList();
        }
        return new EventVenueDtoList(eventVenueDtos);
    }

}
