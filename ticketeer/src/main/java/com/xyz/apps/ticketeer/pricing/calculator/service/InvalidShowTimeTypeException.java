/*
 * Id: InvalidShowTimeTypeException.java 07-Mar-2022 12:43:01 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.service;

/**
 * The invalid show time type exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidShowTimeTypeException extends PricingServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -3476048200437413619L;

    /**
     * Instantiates a new invalid show time type exception.
     *
     * @param showTimeType the show time type
     */
    public InvalidShowTimeTypeException(final String showTimeType) {

        super("Invalid show time type: " + showTimeType);
    }

}
