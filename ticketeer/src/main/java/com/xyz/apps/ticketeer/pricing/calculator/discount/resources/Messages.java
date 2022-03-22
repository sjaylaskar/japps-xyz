/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.resources;

import java.util.ResourceBundle;

import com.xyz.apps.ticketeer.util.MessageUtil;

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

    /** The error message prefix. */
    private static final String ERROR_MESSAGE_PREFIX = "pricing.calculator.discount.message.error.";

    /*
     * Model Messages:
     */
    /** The message error required discount offer code. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_OFFER_CODE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.offerCode" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error length offer code. */
    public static final String MESSAGE_ERROR_LENGTH_OFFER_CODE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "length.offerCode" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required discount strategy. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_STRATEGY = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.discountStrategy" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required min amount. */
    public static final String MESSAGE_ERROR_REQUIRED_MIN_AMOUNT = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.minAmount" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required min seats. */
    public static final String MESSAGE_ERROR_REQUIRED_MIN_SEATS = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.minSeats" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error min value min seats. */
    public static final String MESSAGE_ERROR_MIN_VALUE_MIN_SEATS = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "minValue.minSeats" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required nth seat. */
    public static final String MESSAGE_ERROR_REQUIRED_NTH_SEAT = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.nthSeat" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error min value nth seat. */
    public static final String MESSAGE_ERROR_MIN_VALUE_NTH_SEAT = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "minValue.nthSeat" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required discount type. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_TYPE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.discountType" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required discount value. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_VALUE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.discountValue" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /*
     * Other messages:
     */

    /** The message error required discount add. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_ADD = ERROR_MESSAGE_PREFIX + "required.discountToAdd";

    /** The message error required discount update. */
    public static final String MESSAGE_ERROR_REQUIRED_DISCOUNT_UPDATE = ERROR_MESSAGE_PREFIX + "required.discountToUpdate";

    /** The message error not empty discounts. */
    public static final String MESSAGE_ERROR_NOT_EMPTY_DISCOUNTS_ADD = ERROR_MESSAGE_PREFIX + "notEmpty.discounts";

    /** The message error not empty discounts update. */
    public static final String MESSAGE_ERROR_NOT_EMPTY_DISCOUNTS_UPDATE = ERROR_MESSAGE_PREFIX + "notEmpty.discounts.update";

    /** The message error unique discount offer codes. */
    public static final String MESSAGE_ERROR_UNIQUE_DISCOUNT_OFFER_CODES = ERROR_MESSAGE_PREFIX + "unique.discount.offerCodes";

    /** The message error failure add discount. */
    public static final String MESSAGE_ERROR_FAILURE_ADD_DISCOUNT = ERROR_MESSAGE_PREFIX + "failure.add.discount";

    /** The message error not blank offer code. */
    public static final String MESSAGE_ERROR_NOT_BLANK_OFFER_CODE = ERROR_MESSAGE_PREFIX + "notBlank.offerCode";

    /** The message error already exists offer code. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_OFFER_CODE = ERROR_MESSAGE_PREFIX + "alreadyExists.offerCode";

    /** The message error failure update discount. */
    public static final String MESSAGE_ERROR_FAILURE_UPDATE_DISCOUNT = ERROR_MESSAGE_PREFIX + "failure.update.discount";

    /** The message error not found discount id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_DISCOUNT_ID = ERROR_MESSAGE_PREFIX + "notFound.discount.id";

    /** The message error not found discount offer code. */
    public static final String MESSAGE_ERROR_NOT_FOUND_DISCOUNT_OFFER_CODE = ERROR_MESSAGE_PREFIX + "notFound.discount.offerCode";

    /** The message error not found discounts. */
    public static final String MESSAGE_ERROR_NOT_FOUND_DISCOUNTS = ERROR_MESSAGE_PREFIX + "notFound.discounts";

    /** The message error not blank discount offer code delete. */
    public static final String MESSAGE_ERROR_NOT_BLANK_DISCOUNT_OFFER_CODE_DELETE = ERROR_MESSAGE_PREFIX + "notBlank.discount.offerCode.delete";

    /** The message error not blank discount id delete. */
    public static final String MESSAGE_ERROR_NOT_BLANK_DISCOUNT_ID_DELETE = ERROR_MESSAGE_PREFIX + "notBlank.discount.id.delete";

    /** The message error not blank discount id find. */
    public static final String MESSAGE_ERROR_NOT_BLANK_DISCOUNT_ID_FIND = ERROR_MESSAGE_PREFIX + "notBlank.discount.id.find";

    /** The message error not blank discount id update. */
    public static final String MESSAGE_ERROR_NOT_BLANK_DISCOUNT_ID_UPDATE = ERROR_MESSAGE_PREFIX + "notBlank.discount.id.update";

    /** The message error not found discounts for city. */
    public static final String MESSAGE_ERROR_NOT_FOUND_DISCOUNTS_FOR_CITY = ERROR_MESSAGE_PREFIX + "notFound.discounts.city";

    /** The message error not found discounts for event venue. */
    public static final String MESSAGE_ERROR_NOT_FOUND_DISCOUNTS_FOR_EVENT_VENUE = ERROR_MESSAGE_PREFIX + "notFound.discounts.eventVenue";

    /** The message error invalid city id. */
    public static final String MESSAGE_ERROR_INVALID_CITY_ID = ERROR_MESSAGE_PREFIX + "invalid.city.id";

    /** The message error invalid event venue id. */
    public static final String MESSAGE_ERROR_INVALID_EVENT_VENUE_ID = ERROR_MESSAGE_PREFIX + "invalid.event.venue.id";

    /** The message error invalid discount strategy. */
    public static final String MESSAGE_ERROR_INVALID_DISCOUNT_STRATEGY = ERROR_MESSAGE_PREFIX + "invalid.discount.strategy";

    /** The message error invalid show time type. */
    public static final String MESSAGE_ERROR_INVALID_SHOW_TIME_TYPE = ERROR_MESSAGE_PREFIX + "invalid.show.time.type";

    /** The message error invalid discount type. */
    public static final String MESSAGE_ERROR_INVALID_DISCOUNT_TYPE = ERROR_MESSAGE_PREFIX + "invalid.discount.type";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return MessageUtil.resourceBundle("module.pricing.calculator.discount.Messages");
    }
}
