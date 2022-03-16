/*
* Id: EventShowSeatPricesUpdationDto.java 16-Mar-2022 9:19:12 am SubhajoyLaskar
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
 * The event show seat prices updation dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatPricesRequestDto {

    /** The event show id. */
    private Long eventShowId;

    /** The event show seat numbers. */
    private Set<String> eventShowSeatNumbers = new HashSet<>();

    /**
     * Of.
     *
     * @param eventShowId the event show id
     * @param eventShowSeatNumbers the event show seat numbers
     * @return the event show seat prices request dto
     */
    public static EventShowSeatPricesRequestDto of(final Long eventShowId, final Set<String> eventShowSeatNumbers) {
        final EventShowSeatPricesRequestDto eventShowSeatPricesRequestDto = new EventShowSeatPricesRequestDto();
        if (eventShowId != null && CollectionUtils.isNotEmpty(eventShowSeatNumbers)) {
            eventShowSeatPricesRequestDto.setEventShowId(eventShowId);
            eventShowSeatPricesRequestDto.setEventShowSeatNumbers(eventShowSeatNumbers);
        }
        return eventShowSeatPricesRequestDto;
    }
}
