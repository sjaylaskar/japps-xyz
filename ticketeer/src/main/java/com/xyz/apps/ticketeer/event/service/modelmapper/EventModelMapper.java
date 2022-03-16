/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.event.service.modelmapper;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.event.api.internal.contract.EventDto;
import com.xyz.apps.ticketeer.event.model.Event;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;


/**
 * The event model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventModelMapper extends GeneralModelMapper<Event, EventDto> {

    /**
     * Instantiates a new event model mapper.
     */
    public EventModelMapper() {

        super(Event.class, EventDto.class);
    }
}
