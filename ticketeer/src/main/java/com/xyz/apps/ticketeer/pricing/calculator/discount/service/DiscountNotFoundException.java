/*
* Id: DiscountNotFoundException.java 05-Mar-2022 1:26:54 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;

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
     * @param id the id
     */
    public DiscountNotFoundException(final String id) {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_DISCOUNT_ID, id);
    }

    /**
     * Instantiates a new discount not found exception.
     *
     * @param offerCode the offer code
     */
    public DiscountNotFoundException(final OfferCode offerCode) {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_DISCOUNT_OFFER_CODE, offerCode);
    }

    /**
     * Instantiates a new discount not found exception.
     */
    public DiscountNotFoundException() {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_DISCOUNTS);
    }

    /**
     * Instantiates a new offer code.
     */

    /**
     * Instantiates a new offer code.
     *
     * @param offerCode the offer code
     */

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
