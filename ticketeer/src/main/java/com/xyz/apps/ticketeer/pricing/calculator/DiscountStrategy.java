/*
* Id: DiscountStrategy.java 03-Mar-2022 11:20:36 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator;

import java.util.function.Consumer;

/**
 * The discount strategy.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum DiscountStrategy {

    /** The flat discount. */
    FLAT_DISCOUNT(BookingDiscountApplier::applyFlatDiscount),

    /** The nth seat discount. */
    NTH_SEAT_DISCOUNT(BookingDiscountApplier::applyNthSeatDiscount),

    /** The n seats discount. */
    N_SEATS_DISCOUNT(BookingDiscountApplier::applyNSeatsDiscount),

    /** The show time discount. */
    SHOW_TIME_DISCOUNT(BookingDiscountApplier::applyShowTimeDiscount);

    /** The booking discount applier consumer. */
    private Consumer<BookingDiscountApplier> bookingDiscountApplierConsumer;

    /**
     * Instantiates a new discount strategy.
     *
     * @param bookingDiscountApplierConsumer the booking discount applier consumer
     */
    DiscountStrategy(final Consumer<BookingDiscountApplier> bookingDiscountApplierConsumer) {
        this.bookingDiscountApplierConsumer = bookingDiscountApplierConsumer;
    }

    /**
     * Accept.
     *
     * @param bookingDiscountApplier the booking discount applier
     */
    void accept(final BookingDiscountApplier bookingDiscountApplier) {
        bookingDiscountApplierConsumer.accept(bookingDiscountApplier);
    }
}
