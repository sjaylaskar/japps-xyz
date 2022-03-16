/*
* Id: EventShowSeatModelMapper.java 03-Mar-2022 6:06:14 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper;

import javax.annotation.PostConstruct;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatForShowResponseDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeat;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;


/**
 * The event show seat model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowSeatForShowModelMapper extends GeneralModelMapper<EventShowSeat, EventShowSeatForShowResponseDto> {

    /**
     * Instantiates a new event show seat model mapper.
     */
    public EventShowSeatForShowModelMapper() {

        super(EventShowSeat.class, EventShowSeatForShowResponseDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<EventShowSeat, EventShowSeatForShowResponseDto> eventShowSeatToEventShowSeatDtoMap = modelMapper.createTypeMap(EventShowSeat.class, EventShowSeatForShowResponseDto.class);
        eventShowSeatToEventShowSeatDtoMap
        .addMappings(
          mapper -> mapper.using(ModelConverter.ENUM_TO_NAME_CONVERTER).map(EventShowSeat::getSeatReservationStatus, EventShowSeatForShowResponseDto::setSeatReservationStatus)
        );

    }
}
