/*
* Id: DiscountExternalApiHandlerService.java 14-Mar-2022 12:30:05 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;

import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.external.ExternalApiUrls;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.external.contract.EventVenueDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;
import com.xyz.apps.ticketeer.util.MessageUtil;


/**
 * The discount external api handler service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Validated
@Service
public class DiscountExternalApiHandlerService extends GeneralService {

    /**
     * Validate applicable city ids.
     *
     * @param applicableCityIds the applicable city ids
     */
    public void validateApplicableCityIds(final Set<Long> applicableCityIds) {

        if (CollectionUtils.isNotEmpty(applicableCityIds)) {
            applicableCityIds
                .forEach(cityId -> {
                    try {
                        restTemplate().getForEntity(MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.GET_CITY_BY_ID, cityId), CityDto.class);
                    } catch (final HttpStatusCodeException exception) {
                        throw new DiscountServiceException(Messages.MESSAGE_ERROR_INVALID_CITY_ID, cityId);
                    }
                });
        }
    }

    /**
     * Validate applicable event venue ids.
     *
     * @param applicableEventVenueIds the applicable event venue ids
     */
    public void validateApplicableEventVenueIds(final Set<Long> applicableEventVenueIds) {

        if (CollectionUtils.isNotEmpty(applicableEventVenueIds)) {
            applicableEventVenueIds
                .forEach(eventVenueId -> {
                    try {
                        restTemplate().getForEntity(MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.GET_EVENT_VENUE_BY_ID, eventVenueId), EventVenueDto.class);
                    } catch (final HttpStatusCodeException exception) {
                        throw new DiscountServiceException(Messages.MESSAGE_ERROR_INVALID_EVENT_VENUE_ID, eventVenueId);
                    }
                });
        }
    }
}
