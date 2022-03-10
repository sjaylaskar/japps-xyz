/*
 * Id: DiscountStrategy.java 03-Mar-2022 11:20:36 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.model;

import java.util.Arrays;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.xyz.apps.ticketeer.pricing.calculator.service.BookingDiscountApplier;
import com.xyz.apps.ticketeer.pricing.calculator.service.InvalidDiscountStrategyException;


/**
 * The discount strategy.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum DiscountStrategy {

    /** The flat discount. */
    FLAT_DISCOUNT(BookingDiscountApplier::applyFlatDiscount, "Applicable for flat rate disount."),

    /** The nth seat discount. */
    NTH_SEAT_DISCOUNT(BookingDiscountApplier::applyNthSeatDiscount, "Applicable for discount on nth seat."),

    /** The n seats discount. */
    N_SEATS_DISCOUNT(BookingDiscountApplier::applyNSeatsDiscount, "Applicable discount based on a minimum number of seats."),

    /** The show time discount. */
    SHOW_TIME_DISCOUNT(BookingDiscountApplier::applyShowTimeDiscount, "Applicable for discount based on show time.");

    /** The booking discount applier consumer. */
    private Consumer<BookingDiscountApplier> bookingDiscountApplierConsumer;

    /** The description. */
    private String description;

    /**
     * Instantiates a new discount strategy.
     *
     * @param bookingDiscountApplierConsumer the booking discount applier consumer
     * @param description the description
     */
    DiscountStrategy(final Consumer<BookingDiscountApplier> bookingDiscountApplierConsumer, final String description) {

        this.bookingDiscountApplierConsumer = bookingDiscountApplierConsumer;
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
