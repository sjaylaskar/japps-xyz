/*
* Id: EventVenueModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service.modelmapper;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDto;
import com.xyz.apps.ticketeer.eventvenue.model.EventVenue;
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

    /**
     * Returns an event venue object with only id set.
     *
     * @param id the id
     * @return the event venue
     */
    public EventVenue fromId(final Long id) {
        if (id != null) {
            return new EventVenue().id(id);
        }
        return null;
    }
}
