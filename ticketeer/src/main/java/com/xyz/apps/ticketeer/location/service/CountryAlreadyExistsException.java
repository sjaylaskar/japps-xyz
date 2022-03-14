/*
* Id: CountryAlreadyExistsException.java 14-Mar-2022 6:57:58 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location.service;

import com.xyz.apps.ticketeer.general.service.AlreadyExistsException;
import com.xyz.apps.ticketeer.location.resources.Messages;


/**
 * The country already exists exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CountryAlreadyExistsException extends AlreadyExistsException {

    /** The serial version UID. */
    private static final long serialVersionUID = -5723861400564950734L;

    /**
     * Instantiates a new country already exists exception.
     *
     * @param code the code
     */
    public CountryAlreadyExistsException(final String code) {

        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_ALREADY_EXISTS_COUNTRY_CODE, code);
    }
}
