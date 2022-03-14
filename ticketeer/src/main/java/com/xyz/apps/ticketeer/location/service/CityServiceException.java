/*
 * Id: CityServiceException.java 07-Mar-2022 1:34:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service;

import com.xyz.apps.ticketeer.general.service.ServiceException;
import com.xyz.apps.ticketeer.location.resources.Messages;

/**
 * The city service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CityServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -241616310168263221L;

    public CityServiceException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
