/*
* Id: CountryServiceException.java 07-Mar-2022 1:56:14 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location.service;

import com.xyz.apps.ticketeer.general.service.ServiceException;
import com.xyz.apps.ticketeer.location.resources.Messages;

/**
 * The country service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CountryServiceException extends ServiceException {


    /** The serial version UID. */
    private static final long serialVersionUID = -7930079436782680563L;

    /**
     * Instantiates a new country service exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public CountryServiceException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
