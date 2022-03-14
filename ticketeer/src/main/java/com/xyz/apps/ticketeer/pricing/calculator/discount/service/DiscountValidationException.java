/*
* Id: DiscountValidationException.java 14-Mar-2022 1:10:26 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import com.xyz.apps.ticketeer.general.service.ServiceException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.DiscountValidatorMessages;

/**
 * The discount validation exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class DiscountValidationException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -9176291909622936698L;

    /**
     * Instantiates a new discount validation exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public DiscountValidationException(final String messageKey, final Object ...messageArguments) {

        super(DiscountValidatorMessages.resourceBundle(), messageKey, messageArguments);
    }

}
