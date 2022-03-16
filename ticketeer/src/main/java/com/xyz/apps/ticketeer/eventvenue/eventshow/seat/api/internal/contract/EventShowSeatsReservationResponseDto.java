/*
* Id: EventShowSeatsReservationResponseDto.java 06-Mar-2022 5:21:37 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show seats reservation response dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatsReservationResponseDto {

    /** The event show id. */
    private Long eventShowId;

    /** The seat numbers. */
    private Set<String> seatNumbers = new HashSet<>();

    /** The booking reservation id. */
    private String bookingReservationId;

    private Boolean areSeatsReserved = false;

    /**
     * Of.
     *
     * @param areSeatsReserved the are seats reserved
     * @param bookingReservationId the booking reservation id
     * @param eventShowSeatsReservationRequestDto the event show seats reservation request dto
     * @return the event show seats reservation response dto
     */
    public static EventShowSeatsReservationResponseDto of(final Boolean areSeatsReserved, final String bookingReservationId, final EventShowSeatsReservationRequestDto eventShowSeatsReservationRequestDto) {
        final EventShowSeatsReservationResponseDto eventShowSeatsReservationResponseDto = new EventShowSeatsReservationResponseDto();
        if (areSeatsReserved != null
           && eventShowSeatsReservationRequestDto.getEventShowId() != null
           && CollectionUtils.isNotEmpty(eventShowSeatsReservationRequestDto.getSeatNumbers())
           && StringUtils.isNotBlank(bookingReservationId)) {
            eventShowSeatsReservationResponseDto.setEventShowId(eventShowSeatsReservationRequestDto.getEventShowId());
            eventShowSeatsReservationResponseDto.setBookingReservationId(bookingReservationId);
            eventShowSeatsReservationResponseDto.setSeatNumbers(eventShowSeatsReservationRequestDto.getSeatNumbers());
            eventShowSeatsReservationResponseDto.setAreSeatsReserved(areSeatsReserved);
        }
        return eventShowSeatsReservationResponseDto;
    }
}
