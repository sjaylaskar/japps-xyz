/*
* Id: DiscountValidationException.java 14-Mar-2022 1:10:26 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.service;

import com.xyz.apps.ticketeer.general.service.ServiceException;
import com.xyz.apps.ticketeer.pricing.calculator.resources.BookingDiscountApplierMessages;

/**
 * The booking discount applier exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class BookingDiscountApplierException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -9176291909622936698L;

    /**
     * Instantiates a new booking discount applier exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public BookingDiscountApplierException(final String messageKey, final Object ...messageArguments) {

        super(BookingDiscountApplierMessages.resourceBundle(), messageKey, messageArguments);
    }

}
