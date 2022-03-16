/*
 * Id: EventShowSeatForShowResponseDtoList.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
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
import lombok.ToString;


/**
 * The event show seat for show response dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatForShowResponseDtoList extends DtoList<EventShowSeatForShowResponseDto> {

    /** The id. */
    private Long eventShowId;

    /**
     * Of.
     *
     * @param eventShowId the event show id
     * @param eventShowSeatForShowResponseDtos the event show seat information response dtos
     * @return the event show seat information response dto list
     */
    public static EventShowSeatForShowResponseDtoList of(final Long eventShowId, final List<EventShowSeatForShowResponseDto> eventShowSeatForShowResponseDtos) {
        final EventShowSeatForShowResponseDtoList eventShowSeatInformationResponseDtoList = new EventShowSeatForShowResponseDtoList();
        if (eventShowId != null && CollectionUtils.isNotEmpty(eventShowSeatForShowResponseDtos)) {
            eventShowSeatInformationResponseDtoList.setEventShowId(eventShowId);
            eventShowSeatInformationResponseDtoList.setDtos(eventShowSeatForShowResponseDtos);
        }
        return eventShowSeatInformationResponseDtoList;
    }
}
