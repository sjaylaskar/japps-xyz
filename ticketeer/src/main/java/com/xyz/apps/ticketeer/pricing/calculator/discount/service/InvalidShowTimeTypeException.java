/*
 * Id: InvalidShowTimeTypeException.java 07-Mar-2022 12:43:01 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;

/**
 * The invalid show time type exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidShowTimeTypeException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -3476048200437413619L;

    /**
     * Instantiates a new invalid show time type exception.
     *
     * @param showTimeType the show time type
     */
    public InvalidShowTimeTypeException(final String showTimeType) {

        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_INVALID_SHOW_TIME_TYPE, showTimeType);
    }

}
