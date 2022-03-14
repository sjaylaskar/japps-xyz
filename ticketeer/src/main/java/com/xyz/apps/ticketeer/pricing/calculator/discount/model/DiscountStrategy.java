/*
 * Id: DiscountStrategy.java 03-Mar-2022 11:20:36 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.model;

import java.util.Arrays;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountValidator;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.InvalidDiscountStrategyException;
import com.xyz.apps.ticketeer.pricing.calculator.service.BookingDiscountApplier;


/**
 * The discount strategy.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum DiscountStrategy {

    /** The flat discount. */
    FLAT_DISCOUNT(BookingDiscountApplier::applyFlatDiscount, DiscountValidator::validateFlatDiscount, "Applicable for flat rate disount."),

    /** The nth seat discount. */
    NTH_SEAT_DISCOUNT(BookingDiscountApplier::applyNthSeatDiscount, DiscountValidator::validateNthSeatDiscount, "Applicable for discount on nth seat."),

    /** The n seats discount. */
    N_SEATS_DISCOUNT(BookingDiscountApplier::applyNSeatsDiscount, DiscountValidator::validateNSeatsDiscount, "Applicable discount based on a minimum number of seats."),

    /** The show time discount. */
    SHOW_TIME_DISCOUNT(BookingDiscountApplier::applyShowTimeDiscount, DiscountValidator::validateShowTimeDiscount, "Applicable for discount based on show time.");

    /** The booking discount applier consumer. */
    private Consumer<BookingDiscountApplier> bookingDiscountApplierConsumer;

    /** The discount validator consumer. */
    private Consumer<DiscountValidator> discountValidatorConsumer;

    /** The description. */
    private String description;

    /**
     * Instantiates a new discount strategy.
     *
     * @param bookingDiscountApplierConsumer the booking discount applier consumer
     * @param discountValidatorConsumer the discount validator consumer
     * @param description the description
     */
    DiscountStrategy(final Consumer<BookingDiscountApplier> bookingDiscountApplierConsumer,
                     final Consumer<DiscountValidator> discountValidatorConsumer,
                     final String description) {

        this.bookingDiscountApplierConsumer = bookingDiscountApplierConsumer;
        this.discountValidatorConsumer = discountValidatorConsumer;
        this.description = description;
    }

    /**
     * Accept.
     *
     * @param bookingDiscountApplier the booking discount applier
     */
    public void accept(final BookingDiscountApplier bookingDiscountApplier) {

        bookingDiscountApplierConsumer.accept(bookingDiscountApplier);
    }

    /**
     * Accept.
     *
     * @param discountValidator the discount validator
     */
    public void accept(final DiscountValidator discountValidator) {

        discountValidatorConsumer.accept(discountValidator);
    }

    /**
     * Full description.
     *
     * @return the string
     */
    public String fullDescription() {
        return name() + " : " + description;
    }

    /**
     * Of.
     *
     * @param strategy the strategy
     * @return the discount strategy
     */
    public static DiscountStrategy of(final String strategy) {

        return Arrays.asList(values())
            .stream()
            .filter(value -> StringUtils.equalsIgnoreCase(value.name(), strategy))
            .findFirst()
            .orElseThrow(() -> new InvalidDiscountStrategyException(strategy));
    }
}
