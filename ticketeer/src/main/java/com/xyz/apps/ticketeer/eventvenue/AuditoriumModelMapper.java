/*
* Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.general.AbstractModelMapper;

/**
 * The auditorium model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class AuditoriumModelMapper extends AbstractModelMapper<Auditorium, AuditoriumDto> {

    /**
     * Instantiates a new auditorium model mapper.
     */
    public AuditoriumModelMapper() {

        super(Auditorium.class, AuditoriumDto.class);

        initMappings();
    }

    /**
     * Initializes the mappings.
     */
    private void initMappings() {

        final TypeMap<Auditorium, AuditoriumDto> auditoriumToAuditoriumDtoMap = modelMapper.createTypeMap(Auditorium.class, AuditoriumDto.class);
        auditoriumToAuditoriumDtoMap
        .addMappings(
          mapper -> mapper.map(auditorium -> auditorium.getEventVenue().getId(), AuditoriumDto::setEventVenueId)
        );

        final TypeMap<AuditoriumDto, Auditorium> auditoriumDtoToAuditoriumMap = modelMapper.createTypeMap(AuditoriumDto.class, Auditorium.class);
        auditoriumDtoToAuditoriumMap
        .addMappings(
          mapper -> mapper.map(auditoriumDto -> new EventVenue().id(auditoriumDto.getEventVenueId()), Auditorium::setEventVenue)
        );
    }

}
