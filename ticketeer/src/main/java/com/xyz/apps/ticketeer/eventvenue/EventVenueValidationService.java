/*
* Id: EventVenueValidationService.java 08-Mar-2022 7:22:45 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;

import com.xyz.apps.ticketeer.eventvenue.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.eventvenue.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.eventvenue.api.external.contract.EventDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.EventShowServiceException;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.util.StringUtil;


/**
 * The event venue validation service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class EventVenueValidationService extends GeneralService {

    /**
     * Validate city.
     *
     * @param cityId the city id
     */
    public void validateCity(@NotNull(message = "The city id cannot be null") final Long cityId) {

        try {
            serviceBeansFetcher().restTemplate().getForEntity(
                StringUtil.format(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_CITY_BY_ID.get()), cityId), CityDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new EventShowServiceException(exception.getResponseBodyAsString());
        }
    }

    /**
     * Validate event id.
     *
     * @param eventId the event id
     */
    public void validateEventId(@NotNull(message = "The event id cannot be null") final Long eventId) {
        try {
            serviceBeansFetcher().restTemplate().getForEntity(
                StringUtil.format(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_EVENT_BY_ID.get()), eventId), EventDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new EventShowServiceException(exception.getResponseBodyAsString());
        }
    }
}
