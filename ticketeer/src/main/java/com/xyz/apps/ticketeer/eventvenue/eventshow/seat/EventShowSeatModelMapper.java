/*
* Id: EventShowSeatModelMapper.java 03-Mar-2022 6:06:14 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeat;
import com.xyz.apps.ticketeer.eventvenue.eventshow.EventShow;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;


/**
 * The event show seat model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowSeatModelMapper extends GeneralModelMapper<EventShowSeat, EventShowSeatDto> {

    /**
     * Instantiates a new event show seat model mapper.
     */
    public EventShowSeatModelMapper() {

        super(EventShowSeat.class, EventShowSeatDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<EventShowSeat, EventShowSeatDto> eventShowSeatToEventShowSeatDtoMap = modelMapper.createTypeMap(EventShowSeat.class, EventShowSeatDto.class);
        eventShowSeatToEventShowSeatDtoMap
        .addMappings(
          mapper -> mapper.using(ModelConverter.ENUM_TO_NAME_CONVERTER).map(EventShowSeat::getSeatReservationStatus, EventShowSeatDto::setSeatReservationStatus)
        )
        .addMappings(
            mapper -> mapper.map(eventShowSeat -> eventShowSeat.getEventShow().getId(), EventShowSeatDto::setEventShowId)
          )
        .addMappings(
            mapper -> mapper.map(eventShowSeat -> eventShowSeat.getAuditoriumSeat().getId(), EventShowSeatDto::setAuditoriumSeatId)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALDATETIME_TO_STRING_CONVERTER).map(EventShowSeat::getReservationTime, EventShowSeatDto::setReservationTime)
          );

        final Converter<String, SeatReservationStatus> stringToSeatReservationStatusConverter = converter -> SeatReservationStatus.of(converter.getSource());
        final Converter<Long, EventShow> eventShowIdToEventShowConverter = converter -> new EventShow().id(converter.getSource());
        final Converter<Long, AuditoriumSeat> auditoriumSeatIdToAuditoriumSeatConverter = converter -> new AuditoriumSeat().id(converter.getSource());
        final TypeMap<EventShowSeatDto, EventShowSeat> eventShowSeatDtoToEventShowSeatMap = modelMapper.createTypeMap(EventShowSeatDto.class, EventShowSeat.class);
        eventShowSeatDtoToEventShowSeatMap
        .addMappings(
          mapper -> mapper.using(stringToSeatReservationStatusConverter).map(EventShowSeatDto::getSeatReservationStatus, EventShowSeat::setSeatReservationStatus)
        )
        .addMappings(
            mapper -> mapper.using(eventShowIdToEventShowConverter).map(EventShowSeatDto::getEventShowId, EventShowSeat::setEventShow)
          )
        .addMappings(
            mapper -> mapper.using(auditoriumSeatIdToAuditoriumSeatConverter).map(EventShowSeatDto::getAuditoriumSeatId, EventShowSeat::setAuditoriumSeat)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATETIME_CONVERTER).map(EventShowSeatDto::getReservationTime, EventShowSeat::setReservationTime)
          );
    }

}
