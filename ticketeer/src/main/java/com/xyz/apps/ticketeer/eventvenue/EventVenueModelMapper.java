/*
* Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.AbstractModelMapper;

/**
 * The event venue model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventVenueModelMapper extends AbstractModelMapper<EventVenue, EventVenueDto> {

    public EventVenueModelMapper() {

        super(EventVenue.class, EventVenueDto.class);
    }


}
