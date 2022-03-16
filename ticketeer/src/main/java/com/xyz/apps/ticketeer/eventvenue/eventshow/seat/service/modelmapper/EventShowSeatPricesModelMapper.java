/*
 * Id: EventShowSeatPricesModelMapper.java 03-Mar-2022 6:06:14 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatNumberPriceDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatPricesResponseDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeat;


/**
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowSeatPricesModelMapper {

    /**
     * Instantiates a new event show seat model mapper.
     */
    public EventShowSeatPricesModelMapper() {

    }

    /**
     * To event show seat prices response dto.
     *
     * @param eventShowId the event show id
     * @param eventShowSeats the event show seats
     * @return the event show seat prices response dto
     */
    public EventShowSeatPricesResponseDto toEventShowSeatPricesResponseDto(final Long eventShowId,
            final List<EventShowSeat> eventShowSeats) {

        final EventShowSeatPricesResponseDto eventShowSeatPricesResponseDto = new EventShowSeatPricesResponseDto();
        if (eventShowId != null && CollectionUtils.isNotEmpty(eventShowSeats)) {
            eventShowSeatPricesResponseDto.setEventShowId(eventShowId);
            eventShowSeatPricesResponseDto.setEventShowSeatNumberPrices(eventShowSeats.stream().map(
                eventShowSeat -> EventShowSeatNumberPriceDto.of(eventShowSeat.getSeatNumber(), eventShowSeat.getAmount()))
                .toList());

        }
        return eventShowSeatPricesResponseDto;
    }
}
