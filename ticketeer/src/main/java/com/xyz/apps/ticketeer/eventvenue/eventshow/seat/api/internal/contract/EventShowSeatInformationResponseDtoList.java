/*
 * Id: EventShowSeatInformationResponseDto.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
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
 * The event show seat information response dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatInformationResponseDtoList extends DtoList<EventShowSeatInformationResponseDto> {

    /** The id. */
    private Long eventShowId;

    /**
     * Of.
     *
     * @param eventShowId the event show id
     * @param eventShowSeatInformationResponseDtos the event show seat information response dtos
     * @return the event show seat information response dto list
     */
    public static EventShowSeatInformationResponseDtoList of(final Long eventShowId, final List<EventShowSeatInformationResponseDto> eventShowSeatInformationResponseDtos) {
        final EventShowSeatInformationResponseDtoList eventShowSeatInformationResponseDtoList = new EventShowSeatInformationResponseDtoList();
        if (CollectionUtils.isNotEmpty(eventShowSeatInformationResponseDtos)) {
            eventShowSeatInformationResponseDtoList.setEventShowId(eventShowId);
            eventShowSeatInformationResponseDtoList.setDtos(eventShowSeatInformationResponseDtos);
        }
        return eventShowSeatInformationResponseDtoList;
    }
}
