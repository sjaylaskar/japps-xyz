/*
 * Id: InvalidDiscountStrategyException.java 07-Mar-2022 12:36:48 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;
import com.xyz.apps.ticketeer.util.MessageUtil;

/**
 * The invalid discount strategy exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidDiscountStrategyException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -81004414130541523L;

    /**
     * Instantiates a new invalid discount strategy exception.
     *
     * @param strategy the strategy
     */
    public InvalidDiscountStrategyException(final String strategy) {
        super(MessageUtil.message(Messages.resourceBundle(), Messages.MESSAGE_ERROR_INVALID_DISCOUNT_STRATEGY, strategy));
    }

}
