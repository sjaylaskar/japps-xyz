/*
* Id: EventVenueValidationService.java 08-Mar-2022 7:22:45 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.service.GeneralService;


/**
 * The event venue validation service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class EventVenueValidationService extends GeneralService {

    /** The event venue external api handler service. */
    @Autowired
    private EventVenueExternalApiHandlerService eventVenueExternalApiHandlerService;

    /**
     * Validate city.
     *
     * @param cityId the city id
     */
    public void validateCity(@NotNull(message = "The city id cannot be null") final Long cityId) {
        if (eventVenueExternalApiHandlerService.findCity(cityId) == null) {
            throw new EventVenueServiceException("Invalid city id: " + cityId);
        }
    }

    /**
     * Validate event id.
     *
     * @param eventId the event id
     */
    public void validateEventId(@NotNull(message = "The event id cannot be null") final Long eventId) {
        if (eventVenueExternalApiHandlerService.findEvent(eventId) == null) {
            throw new EventVenueServiceException("Invalid event id: " + eventId);
        }
    }

}
