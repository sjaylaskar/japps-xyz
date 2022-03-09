/*
* Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDetailsDto;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;

/**
 * The auditorium seat model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class AuditoriumSeatModelMapper extends GeneralModelMapper<AuditoriumSeat, AuditoriumSeatDetailsDto> {

    /**
     * Instantiates a new auditorium seat model mapper.
     */
    public AuditoriumSeatModelMapper() {

        super(AuditoriumSeat.class, AuditoriumSeatDetailsDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<AuditoriumSeat, AuditoriumSeatDetailsDto> auditoriumSeatToAuditoriumSeatDetailsDtoMap = modelMapper.createTypeMap(AuditoriumSeat.class, AuditoriumSeatDetailsDto.class);
        auditoriumSeatToAuditoriumSeatDetailsDtoMap
        .addMappings(
          mapper -> mapper.map(auditoriumSeat -> auditoriumSeat.getAuditorium().getId(), AuditoriumSeatDetailsDto::setAuditoriumId)
        );

        final Converter<Long, Auditorium> auditoriumIdToAuditoriumConverter = converter -> new Auditorium().id(converter.getSource());
        final TypeMap<AuditoriumSeatDetailsDto, AuditoriumSeat> auditoriumSeatDetailsDtoToAuditoriumSeatMap = modelMapper.createTypeMap(AuditoriumSeatDetailsDto.class, AuditoriumSeat.class);
        auditoriumSeatDetailsDtoToAuditoriumSeatMap
        .addMappings(
          mapper -> mapper.using(auditoriumIdToAuditoriumConverter).map(AuditoriumSeatDetailsDto::getAuditoriumId, AuditoriumSeat::setAuditorium)
        );
    }

}
