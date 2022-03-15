/*
* Id: AuditoriumModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDtoList;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.BasicAuditoriumDto;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;

/**
 * The auditorium model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class AuditoriumModelMapper extends GeneralModelMapper<Auditorium, AuditoriumDto> {

    /**
     * Instantiates a new auditorium model mapper.
     */
    public AuditoriumModelMapper() {

        super(Auditorium.class, AuditoriumDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<Auditorium, AuditoriumDto> auditoriumToAuditoriumDtoMap = modelMapper.createTypeMap(Auditorium.class, AuditoriumDto.class);
        auditoriumToAuditoriumDtoMap
        .addMappings(
          mapper -> mapper.map(auditorium -> auditorium.getEventVenue().getId(), AuditoriumDto::setEventVenueId)
        );

        final Converter<Long, EventVenue> eventVenueIdToEventVenueConverter = converter -> new EventVenue().id(converter.getSource());
        final TypeMap<AuditoriumDto, Auditorium> auditoriumDtoToAuditoriumMap = modelMapper.createTypeMap(AuditoriumDto.class, Auditorium.class);
        auditoriumDtoToAuditoriumMap
        .addMappings(
          mapper -> mapper.using(eventVenueIdToEventVenueConverter).map(AuditoriumDto::getEventVenueId, Auditorium::setEventVenue)
        );
    }

    /**
     * To auditorium dto list.
     *
     * @param auditoriums the auditoriums
     * @return the auditorium dto list
     */
    public AuditoriumDtoList toAuditoriumDtoList(final List<Auditorium> auditoriums) {
        if (CollectionUtils.isNotEmpty(auditoriums)) {
            return AuditoriumDtoList.of(auditoriums.get(0).getEventVenue().getId(),
                auditoriums.stream().map(auditorium -> BasicAuditoriumDto.of(auditorium.getId(), auditorium.getName())).toList());
        }
        return AuditoriumDtoList.of();
    }

    /**
     * From id.
     *
     * @param id the id
     * @return the auditorium
     */
    public Auditorium fromId(final Long id) {
        if (id != null) {
            return new Auditorium().id(id);
        }
        return null;
    }

}
