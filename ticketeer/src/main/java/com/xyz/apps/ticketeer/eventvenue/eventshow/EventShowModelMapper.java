/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.AbstractModelMapper;


/**
 * The event show model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowModelMapper extends AbstractModelMapper<EventShow, EventShowDto> {

    public EventShowModelMapper() {

        super(EventShow.class, EventShowDto.class);
    }

}
