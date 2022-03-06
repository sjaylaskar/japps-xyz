/*
 * Id: InvalidDiscountTypeException.java 07-Mar-2022 12:45:01 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount;

/**
 * The invalid discount type exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidDiscountTypeException extends DiscountServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 4870015337253355571L;

    /**
     * Instantiates a new invalid discount type exception.
     *
     * @param discountType the discount type
     */
    public InvalidDiscountTypeException(final String discountType) {

        super("Invalid discount type: " + discountType);
    }

}
