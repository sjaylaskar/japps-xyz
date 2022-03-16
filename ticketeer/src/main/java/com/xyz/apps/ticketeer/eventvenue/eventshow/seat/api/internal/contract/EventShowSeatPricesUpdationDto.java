/*
* Id: EventShowSeatPricesUpdationDto.java 16-Mar-2022 9:19:12 am SubhajoyLaskar
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
 * The event show seat prices updation dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatPricesUpdationDto {

    /** The event show id. */
    private Long eventShowId;

    /** The event show seat number prices. */
    private List<EventShowSeatNumberPriceDto> eventShowSeatNumberPrices = new ArrayList<>();
}
