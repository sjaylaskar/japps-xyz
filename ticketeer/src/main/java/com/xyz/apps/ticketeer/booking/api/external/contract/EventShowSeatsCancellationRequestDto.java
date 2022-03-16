/*
* Id: EventShowSeatsCancellationRequestDto.java 16-Mar-2022 2:52:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external.contract;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
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
public class EventShowSeatsCancellationRequestDto {

    /** The event show id. */
    private Long eventShowId;

    /** The seat numbers. */
    private Set<String> seatNumbers = new HashSet<>();

    /** The booking reservation id. */
    private String bookingReservationId;

    /**
     * Of.
     *
     * @param eventShowId the event show id
     * @param seatNumbers the seat numbers
     * @param bookingReservationId the booking reservation id
     * @return the event show seats cancellation request dto
     */
    public static EventShowSeatsCancellationRequestDto of(final Long eventShowId, final Set<String> seatNumbers, final String bookingReservationId) {
        final EventShowSeatsCancellationRequestDto eventShowSeatsCancellationRequestDto = new EventShowSeatsCancellationRequestDto();
        if (eventShowId != null && CollectionUtils.isNotEmpty(seatNumbers) && StringUtils.isNotBlank(bookingReservationId)) {
            eventShowSeatsCancellationRequestDto.setEventShowId(eventShowId);
            eventShowSeatsCancellationRequestDto.setSeatNumbers(seatNumbers);
            eventShowSeatsCancellationRequestDto.setBookingReservationId(bookingReservationId);
        }
        return eventShowSeatsCancellationRequestDto;
    }
}
