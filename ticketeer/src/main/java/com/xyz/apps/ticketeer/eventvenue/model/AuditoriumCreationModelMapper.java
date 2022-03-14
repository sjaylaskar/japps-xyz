/*
* Id: AuditoriumCreationModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumCreationDto;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;

/**
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class AuditoriumCreationModelMapper extends GeneralModelMapper<Auditorium, AuditoriumCreationDto> {

    /**
     */
    public AuditoriumCreationModelMapper() {

        super(Auditorium.class, AuditoriumCreationDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final Converter<Long, EventVenue> eventVenueIdToEventVenueConverter = converter -> new EventVenue().id(converter.getSource());
        final TypeMap<AuditoriumCreationDto, Auditorium> auditoriumDtoToAuditoriumMap = modelMapper.createTypeMap(AuditoriumCreationDto.class, Auditorium.class);
        auditoriumDtoToAuditoriumMap
        .addMappings(
          mapper -> mapper.using(eventVenueIdToEventVenueConverter).map(AuditoriumCreationDto::getEventVenueId, Auditorium::setEventVenue)
        );
    }

}
