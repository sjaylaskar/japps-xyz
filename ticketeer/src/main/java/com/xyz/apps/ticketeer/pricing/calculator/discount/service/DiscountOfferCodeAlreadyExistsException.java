/*
* Id: InvalidOfferCodeException.java 04-Mar-2022 1:58:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import com.xyz.apps.ticketeer.general.service.AlreadyExistsException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;

/**
 * The invalid offer code exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class DiscountOfferCodeAlreadyExistsException extends AlreadyExistsException {

    /** The serial version UID. */
    private static final long serialVersionUID = 3096472426963615107L;

    /**
     * Instantiates a new invalid offer code exception.
     *
     * @param offerCode the offer code
     */
    public DiscountOfferCodeAlreadyExistsException(final String offerCode) {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_ALREADY_EXISTS_OFFER_CODE, offerCode);
    }
}
