/*
* Id: EventVenueExternalApiHandlerService.java 09-Mar-2022 7:05:51 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;

import com.xyz.apps.ticketeer.eventvenue.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.eventvenue.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.eventvenue.api.external.contract.EventDto;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.general.service.ServiceUtil;
import com.xyz.apps.ticketeer.util.StringUtil;

/**
 * The event venue external api handler service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class EventVenueExternalApiHandlerService extends GeneralService {

    /**
     * Finds the city.
     *
     * @param cityId the city id
     * @return the city dto
     */
    public CityDto findCity(@NotNull(message = "The city id cannot be null") final Long cityId) {

        ResponseEntity<CityDto> cityDtoResponseEntity = null;
        try {
            cityDtoResponseEntity = serviceBeansFetcher().restTemplate().getForEntity(
                StringUtil.format(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_CITY_BY_ID.get()), cityId), CityDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new EventVenueServiceException(exception.getResponseBodyAsString());
        }
        if (ServiceUtil.notHasBodyResponseEntity(cityDtoResponseEntity)) {
            throw new EventVenueServiceException("Invalid city id: " + cityId);
        }
        return cityDtoResponseEntity.getBody();
    }

    /**
     * Finds the event.
     *
     * @param eventId the event id
     * @return the event dto
     */
    public EventDto findEvent(@NotNull(message = "The event id cannot be null") final Long eventId) {

        ResponseEntity<EventDto> eventDtoResponseEntity = null;
        try {
            eventDtoResponseEntity = serviceBeansFetcher().restTemplate().getForEntity(
                StringUtil.format(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_EVENT_BY_ID.get()), eventId), EventDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new EventVenueServiceException(exception.getResponseBodyAsString());
        }
        if (ServiceUtil.notHasBodyResponseEntity(eventDtoResponseEntity)) {
            throw new EventVenueServiceException("Invalid event id: " + eventId);
        }
        return eventDtoResponseEntity.getBody();
    }
}