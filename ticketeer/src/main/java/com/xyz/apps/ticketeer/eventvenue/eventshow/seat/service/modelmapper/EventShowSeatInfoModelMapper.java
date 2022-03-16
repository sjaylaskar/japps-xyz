/*
* Id: EventShowSeatModelMapper.java 03-Mar-2022 6:06:14 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper;

import javax.annotation.PostConstruct;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatInformationResponseDto;
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
public class EventShowSeatInfoModelMapper extends GeneralModelMapper<EventShowSeat, EventShowSeatInformationResponseDto> {

    /**
     * Instantiates a new event show seat model mapper.
     */
    public EventShowSeatInfoModelMapper() {

        super(EventShowSeat.class, EventShowSeatInformationResponseDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<EventShowSeat, EventShowSeatInformationResponseDto> eventShowSeatToEventShowSeatDtoMap = modelMapper.createTypeMap(EventShowSeat.class, EventShowSeatInformationResponseDto.class);
        eventShowSeatToEventShowSeatDtoMap
        .addMappings(
          mapper -> mapper.using(ModelConverter.ENUM_TO_NAME_CONVERTER).map(EventShowSeat::getSeatReservationStatus, EventShowSeatInformationResponseDto::setSeatReservationStatus)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALDATETIME_TO_STRING_CONVERTER).map(EventShowSeat::getReservationTime, EventShowSeatInformationResponseDto::setReservationTime)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALDATETIME_TO_STRING_CONVERTER).map(EventShowSeat::getBookingTime, EventShowSeatInformationResponseDto::setBookingTime)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.UUID_TO_STRING_CONVERTER).map(EventShowSeat::getBookingReservationId, EventShowSeatInformationResponseDto::setBookingReservationId)
        );
    }
}
