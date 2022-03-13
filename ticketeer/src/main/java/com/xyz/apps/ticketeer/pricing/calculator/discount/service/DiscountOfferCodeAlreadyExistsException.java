/*
* Id: InvalidOfferCodeException.java 04-Mar-2022 1:58:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import org.springframework.context.MessageSource;

import com.xyz.apps.ticketeer.general.service.AlreadyExistsException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;
import com.xyz.apps.ticketeer.util.MessageUtil;

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
     * @param message the message
     */
    public DiscountOfferCodeAlreadyExistsException(final String message) {
        super(message);
    }

    /**
     * Offer code exists.
     *
     * @param offerCode the offer code
     * @return the discount offer code already exists exception
     */
    public static DiscountOfferCodeAlreadyExistsException offerCodeExists(final MessageSource messageSource, final String offerCode) {
        return new DiscountOfferCodeAlreadyExistsException(MessageUtil.defaultLocaleMessage(messageSource, Messages.MESSAGE_ERROR_ALREADY_EXISTS_OFFER_CODE, offerCode));
    }
}
