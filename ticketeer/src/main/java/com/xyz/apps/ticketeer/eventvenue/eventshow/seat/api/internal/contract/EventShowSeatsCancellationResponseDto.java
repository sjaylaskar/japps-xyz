/*
* Id: EventShowSeatsCancellationRequestDto.java 16-Mar-2022 2:52:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show seats cancellation request dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatsCancellationResponseDto {

    /** The are seats cancelled. */
    private Boolean areSeatsCancelled = false;

    /** The booking reservation id. */
    private String bookingReservationId;

    /**
     * Of.
     *
     * @param areSeatsCancelled the are seats cancelled
     * @param bookingReservationId the booking reservation id
     * @return the event show seats cancellation response dto
     */
    public static EventShowSeatsCancellationResponseDto of(final Boolean areSeatsCancelled, final String bookingReservationId) {
        final EventShowSeatsCancellationResponseDto eventShowSeatsCancellationResponseDto = new EventShowSeatsCancellationResponseDto();
        if (areSeatsCancelled != null && StringUtils.isNotBlank(bookingReservationId)) {
            eventShowSeatsCancellationResponseDto.setAreSeatsCancelled(areSeatsCancelled);
            eventShowSeatsCancellationResponseDto.setBookingReservationId(bookingReservationId);
        }
        return eventShowSeatsCancellationResponseDto;
    }
}
