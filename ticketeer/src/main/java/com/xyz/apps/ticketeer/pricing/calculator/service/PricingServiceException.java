/*
 * Id: PricingServiceException.java 07-Mar-2022 12:41:44 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.service;

import com.xyz.apps.ticketeer.general.service.ServiceException;
import com.xyz.apps.ticketeer.pricing.calculator.resources.Messages;

/**
 * The pricing service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class PricingServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -2600349134221206046L;

    /**
     * Instantiates a new pricing service exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public PricingServiceException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
