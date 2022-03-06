/*
 * Id: InvalidDiscountStrategyException.java 07-Mar-2022 12:36:48 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator;

/**
 * The InvalidDiscountStrategyException.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidDiscountStrategyException extends PricingServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -81004414130541523L;

    /**
     * Instantiates a new invalid discount strategy exception.
     *
     * @param strategy the strategy
     */
    public InvalidDiscountStrategyException(final String strategy) {

        super("Invalid discount strategy: " + strategy);
    }

}
