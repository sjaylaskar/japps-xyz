/*
* Id: EventShowSeatsReservationRequestDto.java 06-Mar-2022 5:21:37 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external.contract;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show seats reservation request dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatsReservationRequestDto {

    /** The event show id. */
    private Long eventShowId;

    /** The seat numbers. */
    private Set<String> seatNumbers = new HashSet<>();

    /**
     * Of.
     *
     * @param eventShowId the event show id
     * @param seatNumbers the seat numbers
     * @return the event show seats reservation request dto
     */
    public static EventShowSeatsReservationRequestDto of(final Long eventShowId, final Set<String> seatNumbers) {
        final EventShowSeatsReservationRequestDto eventShowSeatsReservationRequestDto = new EventShowSeatsReservationRequestDto();
        if (eventShowId != null && CollectionUtils.isNotEmpty(seatNumbers)) {
            eventShowSeatsReservationRequestDto.setEventShowId(eventShowId);
            eventShowSeatsReservationRequestDto.setSeatNumbers(seatNumbers);
        }
        return eventShowSeatsReservationRequestDto;
    }
}
