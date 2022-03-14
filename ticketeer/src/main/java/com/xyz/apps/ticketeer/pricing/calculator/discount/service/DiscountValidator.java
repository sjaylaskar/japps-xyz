/*
 * Id: BookingDiscountApplier.java 04-Mar-2022 12:04:39 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import org.apache.commons.lang3.StringUtils;

import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountContract;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.ShowTimeType;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.DiscountValidatorMessages;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;


/**
 * The discount validator.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class DiscountValidator {

    /** The discount to validate. */
    private DiscountContract discountToValidate;

    /**
     * Instantiates a new discount validator.
     *
     * @param discountToValidate the discount to validate
     */
    public DiscountValidator(final DiscountContract discountToValidate) {

        this.discountToValidate = discountToValidate;
    }

    /**
     * Validate flat discount.
     */
    public void validateFlatDiscount() {

        validate();
    }

    /**
     * Validate nth seat discount.
     */
    public void validateNthSeatDiscount() {

        validate();

        if (discountToValidate.getNthSeat() == null || discountToValidate.getNthSeat() < 0) {
            throw new DiscountValidationException(DiscountValidatorMessages.MESSAGE_ERROR_REQUIRED_NTH_SEAT_FOR_STRATEGY, discountToValidate.getDiscountStrategy());
        }
    }

    /**
     * Validate N seats discount.
     */
    public void validateNSeatsDiscount() {

        validate();

        if (discountToValidate.getMinSeats() == null || discountToValidate.getMinSeats() < 0) {
            throw new DiscountValidationException(DiscountValidatorMessages.MESSAGE_ERROR_REQUIRED_MIN_SEATS_FOR_STRATEGY, discountToValidate.getDiscountStrategy());
        }
    }


    /**
     * Validate show time discount.
     */
    public void validateShowTimeDiscount() {

        validate();

        if (StringUtils.isBlank(discountToValidate.getShowTimeType()) || ShowTimeType.of(discountToValidate.getShowTimeType()) == null) {
            throw new DiscountValidationException(DiscountValidatorMessages.MESSAGE_ERROR_REQUIRED_SHOW_TIME_TYPE_FOR_STRATEGY, discountToValidate.getDiscountStrategy());
        }
    }

    /**
     * Validate.
     */
    private void validate() {

        validateNotNegativeAmounts();

        validateDates();
    }

    /**
     * Validate not negative amounts.
     */
    private void validateNotNegativeAmounts() {
        if (discountToValidate.getMinAmount() != null && discountToValidate.getMinAmount() < 0) {
            throw new DiscountValidationException(DiscountValidatorMessages.MESSAGE_ERROR_NOT_NEGATIVE_MIN_AMOUNT, discountToValidate.getMinAmount());
        }

        if (discountToValidate.getValue() != null && discountToValidate.getValue() < 0) {
            throw new DiscountValidationException(DiscountValidatorMessages.MESSAGE_ERROR_NOT_NEGATIVE_VALUE, discountToValidate.getValue());
        }
    }

    /**
     * Validate date.
     */
    private void validateDates() {
        if ((StringUtils.isNotBlank(discountToValidate.getStartTime()) && StringUtils.isNotBlank(discountToValidate.getEndTime()))
            && LocalDateTimeFormatUtil.parseLocalDateTime(discountToValidate.getStartTime()).isAfter(LocalDateTimeFormatUtil.parseLocalDateTime(discountToValidate.getEndTime()))) {
            throw new DiscountValidationException(DiscountValidatorMessages.MESSAGE_ERROR_START_DATE_AFTER_END_DATE, discountToValidate.getStartTime(), discountToValidate.getEndTime());
        }
    }
}
