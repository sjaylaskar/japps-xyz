/*
* Id: DiscountNotFoundException.java 05-Mar-2022 1:26:54 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The discount not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class DiscountNotFoundException extends DiscountServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 9181617415120914091L;

    /**
     * Instantiates a new discount not found exception.
     *
     * @param id the id
     */
    public DiscountNotFoundException(final String id) {
        super("Discount not found for id: " + id);
    }

    /**
     * Instantiates a new discount not found exception.
     *
     * @param offerCode the offer code
     */
    public DiscountNotFoundException(final OfferCode offerCode) {
        super("Invalid offer code: " + offerCode);
    }

    public DiscountNotFoundException() {
        super("No discounts found.");
    }

    /**
     * The offer code.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    static final class OfferCode {

        /** The offer code. */
        private String offerCode;

        @Override
        public String toString() {

            return offerCode;
        }
    }
}
