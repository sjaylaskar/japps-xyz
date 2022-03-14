/*
* Id: CountryNotFoundException.java 15-Feb-2022 4:57:58 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location.service;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.location.resources.Messages;

/**
 * The country not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CountryNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -2299273285227837285L;

    /**
     * Instantiates a new country not found exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public CountryNotFoundException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * Instantiates a new country not found exception.
     */
    public CountryNotFoundException() {
        this(Messages.MESSAGE_ERROR_NOT_FOUND_COUNTRIES);
    }

    /**
     * Instantiates a new country not found exception.
     *
     * @param countryId the country id
     * @return the country not found exception
     */
    public static CountryNotFoundException forId(final Long countryId) {
        return new CountryNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_COUNTRY_ID, countryId);
    }

    /**
     * Instantiates a new country not found exception.
     *
     * @param code the code
     * @return the country not found exception
     */
    public static CountryNotFoundException forCode(final String code) {
        return new CountryNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_COUNTRY_CODE, code);
    }

    /**
     * For name.
     *
     * @param name the name
     * @return the country not found exception
     */
    public static CountryNotFoundException forName(final String name) {
        return new CountryNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_COUNTRY_NAME, name);
    }

}
