/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.event;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.AbstractModelMapper;


/**
 * The event model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventModelMapper extends AbstractModelMapper<Event, EventDto> {

    public EventModelMapper() {

        super(Event.class, EventDto.class);
    }
}
