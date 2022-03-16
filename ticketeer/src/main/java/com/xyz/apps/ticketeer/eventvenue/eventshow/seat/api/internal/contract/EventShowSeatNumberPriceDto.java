/*
* Id: EventShowSeatNumberPriceDto.java 16-Mar-2022 6:08:18 am SubhajoyLaskar
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
 * The event show seat number price dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatNumberPriceDto {

    /** The seat number. */
    private String seatNumber;

    /** The amount. */
    private Double amount;

    /**
     * Of.
     *
     * @param seatNumber the seat number
     * @param amount the amount
     * @return the event show seat number price dto
     */
    public static EventShowSeatNumberPriceDto of(final String seatNumber, final Double amount) {
        final EventShowSeatNumberPriceDto eventShowSeatNumberPriceDto = new EventShowSeatNumberPriceDto();
        if (StringUtils.isNotBlank(seatNumber) && amount != null) {
            eventShowSeatNumberPriceDto.setSeatNumber(seatNumber);
            eventShowSeatNumberPriceDto.setAmount(amount);
        }
        return eventShowSeatNumberPriceDto;
    }
}
