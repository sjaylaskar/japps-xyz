/*
* Id: EventShowSeatModelMapper.java 03-Mar-2022 6:06:14 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeat;
import com.xyz.apps.ticketeer.eventvenue.eventshow.EventShow;
import com.xyz.apps.ticketeer.model.general.AbstractModelMapper;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;


/**
 * The event show seat model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowSeatModelMapper extends AbstractModelMapper<EventShowSeat, EventShowSeatDto> {

    /**
     * Instantiates a new event show seat model mapper.
     */
    public EventShowSeatModelMapper() {

        super(EventShowSeat.class, EventShowSeatDto.class);

        initMappings();
    }

    /**
     * Initializes the mappings.
     */
    private void initMappings() {

        final TypeMap<EventShowSeat, EventShowSeatDto> eventShowSeatToEventShowSeatDtoMap = modelMapper.createTypeMap(EventShowSeat.class, EventShowSeatDto.class);
        eventShowSeatToEventShowSeatDtoMap
        .addMappings(
          mapper -> mapper.map(eventShowSeat -> eventShowSeat.getSeatReservationStatus().name(), EventShowSeatDto::setSeatReservationStatus)
        )
        .addMappings(
            mapper -> mapper.map(eventShowSeat -> eventShowSeat.getEventShow().getId(), EventShowSeatDto::setEventShowId)
          )
        .addMappings(
            mapper -> mapper.map(eventShowSeat -> eventShowSeat.getAuditoriumSeat().getId(), EventShowSeatDto::setAuditoriumSeatId)
          )
        .addMappings(
            mapper -> mapper.map(eventShowSeat -> LocalDateTimeFormatUtil.format(eventShowSeat.getReservationTime()), EventShowSeatDto::setReservationTime)
          );

        final TypeMap<EventShowSeatDto, EventShowSeat> eventShowSeatDtoToEventShowSeatMap = modelMapper.createTypeMap(EventShowSeatDto.class, EventShowSeat.class);
        eventShowSeatDtoToEventShowSeatMap
        .addMappings(
          mapper -> mapper.map(eventShowSeatDto -> SeatReservationStatus.of(eventShowSeatDto.getSeatReservationStatus()), EventShowSeat::setSeatReservationStatus)
        )
        .addMappings(
            mapper -> mapper.map(eventShowSeatDto -> new EventShow().id(eventShowSeatDto.getEventShowId()), EventShowSeat::setEventShow)
          )
        .addMappings(
            mapper -> mapper.map(eventShowSeatDto -> new AuditoriumSeat().id(eventShowSeatDto.getAuditoriumSeatId()), EventShowSeat::setAuditoriumSeat)
          )
        .addMappings(
            mapper -> mapper.map(eventShowSeatDto -> LocalDateTimeFormatUtil.parseLocalDateTime(eventShowSeatDto.getReservationTime()), EventShowSeat::setReservationTime)
          );
    }

}
