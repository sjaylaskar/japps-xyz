/*
* Id: EventShowSeatNumbersDto.java 09-Mar-2022 6:33:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show seat numbers dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatNumbersDto {

    /** The seat numbers. */
    private List<String> seatNumbers = new ArrayList<>();

    /**
     * Of.
     *
     * @param seatNumbers the seat numbers
     * @return the event show seat numbers dto
     */
    public static EventShowSeatNumbersDto of(final List<String> seatNumbers) {
        final EventShowSeatNumbersDto eventShowSeatNumbersDto = new EventShowSeatNumbersDto();
        eventShowSeatNumbersDto.setSeatNumbers(seatNumbers);
        return eventShowSeatNumbersDto;
    }
}
