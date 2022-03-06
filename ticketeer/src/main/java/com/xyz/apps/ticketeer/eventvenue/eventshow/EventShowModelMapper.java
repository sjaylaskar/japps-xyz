/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.EventVenue;
import com.xyz.apps.ticketeer.model.general.AbstractModelMapper;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;


/**
 * The event show model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowModelMapper extends AbstractModelMapper<EventShow, EventShowDto> {

    /**
     * Instantiates a new event show model mapper.
     */
    public EventShowModelMapper() {

        super(EventShow.class, EventShowDto.class);

        initMappings();
    }

    /**
     * Initializes the mappings.
     */
    private void initMappings() {

        final TypeMap<EventShow, EventShowDto> eventShowToEventShowDtoMap = modelMapper.createTypeMap(EventShow.class, EventShowDto.class);
        eventShowToEventShowDtoMap
        .addMappings(
          mapper -> mapper.map(eventShow -> LocalDateTimeFormatUtil.format(eventShow.getDate()), EventShowDto::setDate)
        )
        .addMappings(
            mapper -> mapper.map(eventShow -> LocalDateTimeFormatUtil.format(eventShow.getStartTime()), EventShowDto::setStartTime)
        )
        .addMappings(
            mapper -> mapper.map(eventShow -> LocalDateTimeFormatUtil.format(eventShow.getEndTime()), EventShowDto::setEndTime)
        )
        .addMappings(
            mapper -> mapper.map(eventShow -> eventShow.getAuditorium().getId(), EventShowDto::setAuditoriumId)
        )
        .addMappings(
            mapper -> mapper.map(eventShow -> eventShow.getEventVenue().getId(), EventShowDto::setEventVenueId)
        );

        final TypeMap<EventShowDto, EventShow> eventShowDtoToEventShowMap = modelMapper.createTypeMap(EventShowDto.class, EventShow.class);
        eventShowDtoToEventShowMap
        .addMappings(
          mapper -> mapper.map(eventShowDto -> LocalDateTimeFormatUtil.parseLocalDate(eventShowDto.getDate()), EventShow::setDate)
        )
        .addMappings(
            mapper -> mapper.map(eventShowDto -> LocalDateTimeFormatUtil.parseLocalTime(eventShowDto.getStartTime()), EventShow::setStartTime)
          )
        .addMappings(
            mapper -> mapper.map(eventShowDto -> LocalDateTimeFormatUtil.parseLocalTime(eventShowDto.getEndTime()), EventShow::setEndTime)
          )
        .addMappings(
            mapper -> mapper.map(eventShowDto -> new Auditorium().id(eventShowDto.getAuditoriumId()), EventShow::setAuditorium)
        )
        .addMappings(
            mapper -> mapper.map(eventShowDto -> new EventVenue().id(eventShowDto.getEventVenueId()), EventShow::setEventVenue)
        );
    }

}
