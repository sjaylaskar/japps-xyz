/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.event;

import javax.annotation.PostConstruct;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;


/**
 * The event model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventDetailsModelMapper extends GeneralModelMapper<EventDetails, EventDetailsDto> {

    /**
     * Instantiates a new event details model mapper.
     */
    public EventDetailsModelMapper() {
        super(EventDetails.class, EventDetailsDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {
        final TypeMap<EventDetails, EventDetailsDto> eventDetailsToEventDetailsDtoMap = modelMapper.createTypeMap(EventDetails.class, EventDetailsDto.class);
        eventDetailsToEventDetailsDtoMap.addMappings(
          mapper -> mapper.using(ModelConverter.LOCALDATE_TO_STRING_CONVERTER).map(EventDetails::getReleaseDate, EventDetailsDto::setReleaseDate)
        );

        final TypeMap<EventDetailsDto, EventDetails> eventDetailsDtoToEventDetailsMap = modelMapper.createTypeMap(EventDetailsDto.class, EventDetails.class);
        eventDetailsDtoToEventDetailsMap.addMappings(
          mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATE_CONVERTER).map(EventDetailsDto::getReleaseDate, EventDetails::setReleaseDate)
        );
    }

}
