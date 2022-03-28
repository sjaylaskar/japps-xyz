/*
* Id: EventShowDetailsDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show details dto list.
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowDetailsDtoList {

    /** The event show details list. */
    private List<EventShowDetailsDto> eventShowDetailsList;

    /**
     * Instantiates a new event show details dto list.
     */
    EventShowDetailsDtoList() {

    }

    /**
     * Instantiates a new event show details dto list.
     *
     * @param eventShowDetailsList the event show details list
     */
    EventShowDetailsDtoList(final List<EventShowDetailsDto> eventShowDetailsList) {
        this.eventShowDetailsList = eventShowDetailsList;
    }

    /**
     * Of.
     *
     * @param eventShowDetailsList the event show details list
     * @return the event show details dto list
     */
    public static EventShowDetailsDtoList of(final List<EventShowDetailsDto> eventShowDetailsList) {
        if (CollectionUtils.isEmpty(eventShowDetailsList)) {
            return new EventShowDetailsDtoList();
        }
        return new EventShowDetailsDtoList(eventShowDetailsList);
    }
}
