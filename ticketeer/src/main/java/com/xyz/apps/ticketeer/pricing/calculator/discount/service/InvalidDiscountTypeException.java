/*
 * Id: InvalidDiscountTypeException.java 07-Mar-2022 12:45:01 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;

/**
 * The invalid discount type exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidDiscountTypeException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = 4870015337253355571L;

    /**
     * Instantiates a new invalid discount type exception.
     *
     * @param discountType the discount type
     */
    public InvalidDiscountTypeException(final String discountType) {

        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_INVALID_DISCOUNT_TYPE, discountType);
    }
}
