/*
* Id: EventShowSeatsBookingRequestDto.java 06-Mar-2022 5:21:37 pm SubhajoyLaskar
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
 * The event show seats booking request dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatsBookingRequestDto {

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
     * @param bookingReservationId the booking reservation id
     * @param seatNumbers the seat numbers
     * @return the event show seats booking request dto
     */
    public static EventShowSeatsBookingRequestDto of(final Long eventShowId, final String bookingReservationId, final Set<String> seatNumbers) {
        final EventShowSeatsBookingRequestDto eventShowSeatsBookingRequestDto = new EventShowSeatsBookingRequestDto();

        if (eventShowId != null
            && StringUtils.isNotBlank(bookingReservationId)
            && CollectionUtils.isNotEmpty(seatNumbers)) {
           eventShowSeatsBookingRequestDto.setEventShowId(eventShowId);
           eventShowSeatsBookingRequestDto.setBookingReservationId(bookingReservationId);
           eventShowSeatsBookingRequestDto.setSeatNumbers(seatNumbers);
        }
        return eventShowSeatsBookingRequestDto;
    }
}
