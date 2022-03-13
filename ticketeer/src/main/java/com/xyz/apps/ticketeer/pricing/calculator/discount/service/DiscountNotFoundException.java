/*
* Id: DiscountNotFoundException.java 05-Mar-2022 1:26:54 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import org.springframework.context.MessageSource;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;
import com.xyz.apps.ticketeer.util.MessageUtil;

import lombok.AllArgsConstructor;

/**
 * The discount not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class DiscountNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = 9181617415120914091L;

    /**
     * Instantiates a new discount not found exception.
     *
     * @param messageSource the message source
     * @param id the id
     */
    public DiscountNotFoundException(final MessageSource messageSource, final String id) {
        super(MessageUtil.defaultLocaleMessage(messageSource, Messages.MESSAGE_ERROR_NOT_FOUND_DISCOUNT_ID, id));
    }

    /**
     * Instantiates a new discount not found exception.
     *
     * @param messageSource the message source
     * @param offerCode the offer code
     */
    public DiscountNotFoundException(final MessageSource messageSource, final OfferCode offerCode) {
        super(MessageUtil.defaultLocaleMessage(messageSource, Messages.MESSAGE_ERROR_NOT_FOUND_DISCOUNT_OFFER_CODE, offerCode));
    }

    /**
     * Instantiates a new discount not found exception.
     *
     * @param messageSource the message source
     */
    public DiscountNotFoundException(final MessageSource messageSource) {
        super(MessageUtil.defaultLocaleMessage(messageSource, Messages.MESSAGE_ERROR_NOT_FOUND_DISCOUNTS, null));
    }

    /**
     * Instantiates a new offer code.
     *
     * @param offerCode the offer code
     */
    @AllArgsConstructor
    static final class OfferCode {

        /** The offer code. */
        private String offerCode;

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {

            return offerCode;
        }
    }
}
