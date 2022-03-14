/*
 * Id: CityNotFoundException.java 07-Mar-2022 1:35:11 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.location.resources.Messages;

/**
 * The city not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CityNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -6156356781173186496L;

    /**
     * Instantiates a new city not found exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public CityNotFoundException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * Instantiates a new city not found exception.
     */
    public CityNotFoundException() {
        this(Messages.MESSAGE_ERROR_NOT_FOUND_CITIES);
    }

    /**
     * Instantiates a new city not found exception.
     *
     * @param cityId the city id
     * @return the city not found exception
     */
    public static CityNotFoundException forId(final Long cityId) {

        return new CityNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_CITY_ID, cityId);
    }

    /**
     * Instantiates a new city not found exception.
     *
     * @param code the code
     * @return the city not found exception
     */
    public static CityNotFoundException forCode(final String code) {

        return new CityNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_CITY_CODE, code);
    }

    /**
     * For country id.
     *
     * @param countryId the country id
     * @return the city not found exception
     */
    public static CityNotFoundException forCountryId(final Long countryId) {

        return new CityNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_CITY_FOR_COUNTRY, countryId);
    }

    /**
     * Instantiates a new city not found exception.
     *
     * @param name the name
     * @return the city not found exception
     */
    public static CityNotFoundException forName(final String name) {

        return new CityNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_CITY_NAME, name);
    }
}
