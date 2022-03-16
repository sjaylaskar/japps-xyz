/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.service.modelmapper;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShow;
import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;


/**
 * The event show model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowModelMapper extends GeneralModelMapper<EventShow, EventShowDto> {

    /**
     * Instantiates a new event show model mapper.
     */
    public EventShowModelMapper() {

        super(EventShow.class, EventShowDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<EventShow, EventShowDto> eventShowToEventShowDtoMap = modelMapper.createTypeMap(EventShow.class, EventShowDto.class);
        eventShowToEventShowDtoMap
        .addMappings(
          mapper -> mapper.using(ModelConverter.LOCALDATE_TO_STRING_CONVERTER).map(EventShow::getDate, EventShowDto::setDate)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALTIME_TO_STRING_CONVERTER).map(EventShow::getStartTime, EventShowDto::setStartTime)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALDATE_TO_STRING_CONVERTER).map(EventShow::getEndDate, EventShowDto::setEndDate)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALTIME_TO_STRING_CONVERTER).map(EventShow::getEndTime, EventShowDto::setEndTime)
        )
        .addMappings(
            mapper -> mapper.map(eventShow -> eventShow.getAuditorium().getId(), EventShowDto::setAuditoriumId)
        );

        final Converter<Long, Auditorium> auditoriumIdToAuditoriumConverter = converter -> new Auditorium().id(converter.getSource());
        final TypeMap<EventShowDto, EventShow> eventShowDtoToEventShowMap = modelMapper.createTypeMap(EventShowDto.class, EventShow.class);
        eventShowDtoToEventShowMap
        .addMappings(
          mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATE_CONVERTER).map(EventShowDto::getDate, EventShow::setDate)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALTIME_CONVERTER).map(EventShowDto::getStartTime, EventShow::setStartTime)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATE_CONVERTER).map(EventShowDto::getEndDate, EventShow::setEndDate)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALTIME_CONVERTER).map(EventShowDto::getEndTime, EventShow::setEndTime)
          )
        .addMappings(
            mapper -> mapper.using(auditoriumIdToAuditoriumConverter).map(EventShowDto::getAuditoriumId, EventShow::setAuditorium)
        );
    }

    /**
     * Returns an event show object with only id set.
     *
     * @param id the id
     * @return the event venue
     */
    public EventShow fromId(final Long id) {
        if (id != null) {
            return new EventShow().id(id);
        }
        return null;
    }
}
