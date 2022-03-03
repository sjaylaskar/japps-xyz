/*
* Id: EventShowSeatModelMapper.java 03-Mar-2022 6:06:14 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.AbstractModelMapper;


/**
 * The event show seat model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowSeatModelMapper extends AbstractModelMapper<EventShowSeat, EventShowSeatDto> {

    /**
     * Instantiates a new event show seat model mapper.
     */
    public EventShowSeatModelMapper() {

        super(EventShowSeat.class, EventShowSeatDto.class);
    }

}
