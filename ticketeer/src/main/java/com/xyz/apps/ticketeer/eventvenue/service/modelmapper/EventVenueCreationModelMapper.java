/*
* Id: EventVenueCreationModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service.modelmapper;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueCreationDto;
import com.xyz.apps.ticketeer.eventvenue.model.EventVenue;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;

/**
 * The event venue creation model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventVenueCreationModelMapper extends GeneralModelMapper<EventVenue, EventVenueCreationDto> {

    /**
     * Instantiates a new event venue creation model mapper.
     */
    public EventVenueCreationModelMapper() {

        super(EventVenue.class, EventVenueCreationDto.class);
    }
}
