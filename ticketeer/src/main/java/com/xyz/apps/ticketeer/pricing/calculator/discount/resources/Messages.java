/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.resources;

import java.util.ResourceBundle;

import com.xyz.apps.ticketeer.util.StringUtil;

/**
 * The messages.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Messages {

    /**
     * Instantiates a new messages.
     */
    private Messages() {

    }

    /*
     * Model Messages:
     */
    /** The message error required discount offer code. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_OFFER_CODE = "{pricing.calculator.discount.message.error.required.offerCode}";

    /** The message error length offer code. */
    public static final String MESSAGE_ERROR_LENGTH_OFFER_CODE = "{pricing.calculator.discount.message.error.length.offerCode}";

    /** The message error required discount strategy. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_STRATEGY = "{pricing.calculator.discount.message.error.required.discountStrategy}";

    /** The message error required min amount. */
    public static final String MESSAGE_ERROR_REQUIRED_MIN_AMOUNT = "{pricing.calculator.discount.message.error.required.minAmount}";

    /** The message error required min seats. */
    public static final String MESSAGE_ERROR_REQUIRED_MIN_SEATS = "{pricing.calculator.discount.message.error.required.minSeats}";

    /** The message error min value min seats. */
    public static final String MESSAGE_ERROR_MIN_VALUE_MIN_SEATS = "{pricing.calculator.discount.message.error.minValue.minSeats}";

    /** The message error required nth seat. */
    public static final String MESSAGE_ERROR_REQUIRED_NTH_SEAT = "{pricing.calculator.discount.message.error.required.nthSeat}";

    /** The message error min value nth seat. */
    public static final String MESSAGE_ERROR_MIN_VALUE_NTH_SEAT = "{pricing.calculator.discount.message.error.minValue.nthSeat}";

    /** The message error required discount type. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_TYPE = "{pricing.calculator.discount.message.error.required.discountType}";

    /** The message error required discount value. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_VALUE = "{pricing.calculator.discount.message.error.required.discountValue}";

    /*
     * Service messages:
     */

    /** The message error required discount add. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_ADD = StringUtil.MESSAGE_KEY + "pricing.calculator.discount.message.error.required.discountToAdd";

    /** The message error required discount update. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_UPDATE = StringUtil.MESSAGE_KEY + "pricing.calculator.discount.message.error.required.discountToUpdate";

    /** The message error not empty discounts. */
    public static final String MESSAGE_ERROR_NOT_EMPTY_DISCOUNTS_ADD = StringUtil.MESSAGE_KEY + "pricing.calculator.discount.message.error.notEmpty.discounts";

    /** The message error not empty discounts update. */
    public static final String MESSAGE_ERROR_NOT_EMPTY_DISCOUNTS_UPDATE = StringUtil.MESSAGE_KEY + "pricing.calculator.discount.message.error.notEmpty.discounts.update";

    /** The message error unique discount offer codes. */
    public static final String MESSAGE_ERROR_UNIQUE_DISCOUNT_OFFER_CODES = "pricing.calculator.discount.message.error.unique.discount.offerCodes";

    /** The message error failure add discount. */
    public static final String MESSAGE_ERROR_FAILURE_ADD_DISCOUNT = "pricing.calculator.discount.message.error.failure.add.discount";

    /** The message error not blank offer code. */
    public static final String MESSAGE_ERROR_NOT_BLANK_OFFER_CODE = "pricing.calculator.discount.message.error.notBlank.offerCode";

    /** The message error already exists offer code. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_OFFER_CODE = "pricing.calculator.discount.message.error.alreadyExists.offerCode";

    /** The message error failure update discount. */
    public static final String MESSAGE_ERROR_FAILURE_UPDATE_DISCOUNT = "pricing.calculator.discount.message.error.failure.update.discount";

    /** The message error not found discount id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_DISCOUNT_ID = "pricing.calculator.discount.message.error.notFound.discount.id";

    /** The message error not found discount offer code. */
    public static final String MESSAGE_ERROR_NOT_FOUND_DISCOUNT_OFFER_CODE = "pricing.calculator.discount.message.error.notFound.discount.offerCode";

    /** The message error not found discounts. */
    public static final String MESSAGE_ERROR_NOT_FOUND_DISCOUNTS = "pricing.calculator.discount.message.error.notFound.discounts";

    /** The message error invalid discount strategy. */
    public static final String MESSAGE_ERROR_INVALID_DISCOUNT_STRATEGY = "pricing.calculator.discount.message.error.invalid.discount.strategy";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("messages.pricing.calculator.discount.Messages");
    }
}
