/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.event;

import java.time.LocalDate;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.AbstractModelMapper;


/**
 * The event model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventDetailsModelMapper extends AbstractModelMapper<EventDetails, EventDetailsDto> {

    /**
     * Instantiates a new event details model mapper.
     */
    public EventDetailsModelMapper() {
        super(EventDetails.class, EventDetailsDto.class);

        initMappings();
    }

    /**
     * Initializes the mappings.
     */
    private void initMappings() {

        final TypeMap<EventDetails, EventDetailsDto> eventDetailsToEventDetailsDtoMap = modelMapper.createTypeMap(EventDetails.class, EventDetailsDto.class);
        eventDetailsToEventDetailsDtoMap.addMappings(
          mapper -> mapper.map(eventDetails -> eventDetails.getReleaseDate().toString(), EventDetailsDto::setReleaseDate)
        );

        final TypeMap<EventDetailsDto, EventDetails> eventDetailsDtoToEventDetailsMap = modelMapper.createTypeMap(EventDetailsDto.class, EventDetails.class);
        eventDetailsDtoToEventDetailsMap.addMappings(
          mapper -> mapper.map(eventDetailsDto -> LocalDate.parse(eventDetailsDto.getReleaseDate()), EventDetails::setReleaseDate)
        );
    }

}
