/*
* Id: InvalidOfferCodeException.java 04-Mar-2022 1:58:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;


/**
 * The invalid offer code exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidOfferCodeException extends DiscountServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 3096472426963615107L;

    /**
     * Instantiates a new invalid offer code exception.
     *
     * @param message the message
     */
    public InvalidOfferCodeException(final String message) {
        super(message);
    }

    public static InvalidOfferCodeException offerCodeExists(final String offerCode) {
        return new InvalidOfferCodeException("The offer code: " + offerCode + " already exists. Please add a different offer code.");
    }
}
