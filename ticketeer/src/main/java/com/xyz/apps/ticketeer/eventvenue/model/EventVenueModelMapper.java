/*
* Id: EventVenueModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDto;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;

/**
 * The event venue model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventVenueModelMapper extends GeneralModelMapper<EventVenue, EventVenueDto> {

    /**
     * Instantiates a new event venue model mapper.
     */
    public EventVenueModelMapper() {

        super(EventVenue.class, EventVenueDto.class);
    }
}
