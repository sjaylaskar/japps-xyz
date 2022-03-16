/*
* Id: EventShowSeatsBookingResponseDto.java 06-Mar-2022 5:21:37 pm SubhajoyLaskar
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
 * The event show seats booking response dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatsBookingResponseDto {

    /** The event show id. */
    private Long eventShowId;

    /** The seat numbers. */
    private Set<String> seatNumbers = new HashSet<>();

    /** The booking reservation id. */
    private String bookingReservationId;

    /** The are seats booked. */
    private Boolean areSeatsBooked = false;

    /**
     * Of.
     *
     * @param areSeatsBooked the are seats booked
     * @param eventShowSeatsBookingRequestDto the event show seats booking request dto
     * @return the event show seats booking response dto
     */
    public static EventShowSeatsBookingResponseDto of(final Boolean areSeatsBooked, final EventShowSeatsBookingRequestDto eventShowSeatsBookingRequestDto) {
        final EventShowSeatsBookingResponseDto eventShowSeatsBookingResponseDto = new EventShowSeatsBookingResponseDto();
        if (areSeatsBooked != null
           && eventShowSeatsBookingRequestDto.getEventShowId() != null
           && CollectionUtils.isNotEmpty(eventShowSeatsBookingRequestDto.getSeatNumbers())
           && StringUtils.isNotBlank(eventShowSeatsBookingRequestDto.getBookingReservationId())) {
            eventShowSeatsBookingResponseDto.setEventShowId(eventShowSeatsBookingRequestDto.getEventShowId());
            eventShowSeatsBookingResponseDto.setBookingReservationId(eventShowSeatsBookingRequestDto.getBookingReservationId());
            eventShowSeatsBookingResponseDto.setSeatNumbers(eventShowSeatsBookingRequestDto.getSeatNumbers());
            eventShowSeatsBookingResponseDto.setAreSeatsBooked(areSeatsBooked);

        }
        return eventShowSeatsBookingResponseDto;
    }
}
