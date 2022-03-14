/*
* Id: DiscountServiceException.java 05-Mar-2022 2:39:37 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import com.xyz.apps.ticketeer.general.service.ServiceException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;

/**
 * The discount service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class DiscountServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -6237952517165449903L;

    /**
     * Instantiates a new discount service exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public DiscountServiceException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
