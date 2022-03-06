/*
* Id: DiscountServiceException.java 05-Mar-2022 2:39:37 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import com.xyz.apps.ticketeer.pricing.calculator.PricingServiceException;

/**
 * The discount service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class DiscountServiceException extends PricingServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -6237952517165449903L;

    /**
     * Instantiates a new discount service exception.
     *
     * @param message the message
     */
    public DiscountServiceException(final String message) {
        super(message);
    }
}
