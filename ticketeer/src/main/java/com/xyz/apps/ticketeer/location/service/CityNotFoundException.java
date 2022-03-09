/*
 * Id: CityNotFoundException.java 07-Mar-2022 1:35:11 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service;

import com.xyz.apps.ticketeer.location.api.internal.contract.CityDto;

/**
 * The city not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CityNotFoundException extends CityServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -6156356781173186496L;

    /**
     * Instantiates a new city not found exception.
     *
     * @param cityDto the city dto
     */
    public CityNotFoundException(final CityDto cityDto) {

        super("City not found: " + cityDto);
    }

    /**
     * Instantiates a new city not found exception.
     *
     * @param cityId the city id
     */
    public CityNotFoundException(final Long cityId) {

        super("City not found for id: " + cityId);
    }

    /**
     * Instantiates a new city not found exception.
     *
     * @param codeOrName the code or name
     */
    public CityNotFoundException(final String codeOrName) {

        super("City not found: " + codeOrName);
    }
}
