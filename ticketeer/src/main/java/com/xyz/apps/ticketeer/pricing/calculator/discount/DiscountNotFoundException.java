/*
* Id: DiscountNotFoundException.java 05-Mar-2022 1:26:54 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount;


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
    public DiscountNotFoundException(final Long id) {
        super("Discount not found for id: " + id);
    }

    /**
     * Instantiates a new discount not found exception.
     *
     * @param offerCode the offer code
     */
    public DiscountNotFoundException(final String offerCode) {
        super("Invalid offer code: " + offerCode);
    }

    public DiscountNotFoundException() {
        super("No discounts found.");
    }
}
