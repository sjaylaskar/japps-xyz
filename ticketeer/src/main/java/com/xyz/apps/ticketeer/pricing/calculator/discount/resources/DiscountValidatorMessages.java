/*
* Id: DiscountValidatorMessages.java 14-Mar-2022 1:17:34 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.resources;

import java.util.ResourceBundle;

/**
 * The discount validator messages.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class DiscountValidatorMessages {

    /**
     * Instantiates a new discount validator messages.
     */
    private DiscountValidatorMessages() {

    }

    /** The discount validation error message prefix. */
    private static final String DISCOUNT_VALIDATION_ERROR_MESSAGE_PREFIX = "pricing.calculator.discount.validation.error.";

    /** The message error not negative min amount. */
    public static final String MESSAGE_ERROR_NOT_NEGATIVE_MIN_AMOUNT = DISCOUNT_VALIDATION_ERROR_MESSAGE_PREFIX + "not.negative.min.amount";

    /** The message error not negative value. */
    public static final String MESSAGE_ERROR_NOT_NEGATIVE_VALUE = DISCOUNT_VALIDATION_ERROR_MESSAGE_PREFIX + "not.negative.value";

    /** The message error required nth seat for strategy. */
    public static final String MESSAGE_ERROR_REQUIRED_NTH_SEAT_FOR_STRATEGY = DISCOUNT_VALIDATION_ERROR_MESSAGE_PREFIX + "required.nthSeat";

    /** The message error required min seats for strategy. */
    public static final String MESSAGE_ERROR_REQUIRED_MIN_SEATS_FOR_STRATEGY = DISCOUNT_VALIDATION_ERROR_MESSAGE_PREFIX + "required.minSeats";

    /** The message error required show time type for strategy. */
    public static final String MESSAGE_ERROR_REQUIRED_SHOW_TIME_TYPE_FOR_STRATEGY = DISCOUNT_VALIDATION_ERROR_MESSAGE_PREFIX + "required.showTimeType";

    /** The message error start date after end date. */
    public static final String MESSAGE_ERROR_START_DATE_AFTER_END_DATE = DISCOUNT_VALIDATION_ERROR_MESSAGE_PREFIX + "start.date.after.end.date";


    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("messages.pricing.calculator.discount.DiscountValidatorMessages");
    }
}
