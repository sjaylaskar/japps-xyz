/*
* Id: DiscountValidatorMessages.java 14-Mar-2022 1:17:34 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.resources;

import java.util.ResourceBundle;

/**
 * The booking discount applier messages.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class BookingDiscountApplierMessages {

    /**
     * Instantiates a new booking discount applier messages.
     */
    private BookingDiscountApplierMessages() {

    }

    /** The booking discount applier error message prefix. */
    private static final String BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX = "pricing.calculator.booking.discount.applier.error.";

    /** The message error not applicable offer code at time. */
    public static final String MESSAGE_ERROR_NOT_APPLICABLE_OFFER_CODE_AT_TIME = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "not.applicable.offerCode.at.time";

    /** The message error min amount for offer code. */
    public static final String MESSAGE_ERROR_MIN_AMOUNT_FOR_OFFER_CODE = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "min.amount.for.offerCode";

    /** The message error min seats for offer code. */
    public static final String MESSAGE_ERROR_MIN_SEATS_FOR_OFFER_CODE = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "min.seats.for.offerCode";

    /** The message error not applicable for city. */
    public static final String MESSAGE_ERROR_NOT_APPLICABLE_FOR_CITY = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "not.applicable.for.city";

    /** The message error not applicable for event venue. */
    public static final String MESSAGE_ERROR_NOT_APPLICABLE_FOR_EVENT_VENUE = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "not.applicable.for.event.venue";

    /** The message error applicable for show time. */
    public static final String MESSAGE_ERROR_APPLICABLE_FOR_SHOW_TIME = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "applicable.for.show.time";

    /** The message error not null or negative base amount. */
    public static final String MESSAGE_ERROR_NOT_NULL_OR_NEGATIVE_BASE_AMOUNT = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "notNullOrNegative.baseAmount";

    /** The message error not empty seat prices. */
    public static final String MESSAGE_ERROR_NOT_EMPTY_SEAT_PRICES = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "notEmpty.seatPrices";

    /** The message error number of seats not equal seat prices. */
    public static final String MESSAGE_ERROR_NUMBER_OF_SEATS_NOT_EQUAL_SEAT_PRICES = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "numberOfSeats.notEqual.seatPrices";

    /** The message error not null or negative seat price. */
    public static final String MESSAGE_ERROR_NOT_NULL_OR_NEGATIVE_SEAT_PRICE = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "notNullOrNegative.seatPrice";

    /** The message error base amount not equal sum seat prices. */
    public static final String MESSAGE_ERROR_BASE_AMOUNT_NOT_EQUAL_SUM_SEAT_PRICES = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "baseAmount.notEqual.sum.seatPrices";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("module.pricing.calculator.BookingDiscountApplierMessages");
    }
}
