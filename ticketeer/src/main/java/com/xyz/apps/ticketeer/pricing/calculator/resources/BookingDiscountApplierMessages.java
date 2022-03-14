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

    private BookingDiscountApplierMessages() {

    }

    private static final String BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX = "pricing.calculator.booking.discount.applier.error.";

    public static final String MESSAGE_ERROR_NOT_APPLICABLE_OFFER_CODE_AT_TIME = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "not.applicable.offerCode.at.time";

    public static final String MESSAGE_ERROR_MIN_AMOUNT_FOR_OFFER_CODE = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "min.amount.for.offerCode";

    public static final String MESSAGE_ERROR_MIN_SEATS_FOR_OFFER_CODE = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "min.seats.for.offerCode";

    public static final String MESSAGE_ERROR_NOT_APPLICABLE_FOR_CITY = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "not.applicable.for.city";

    public static final String MESSAGE_ERROR_NOT_APPLICABLE_FOR_EVENT_VENUE = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "not.applicable.for.event.venue";

    public static final String MESSAGE_ERROR_APPLICABLE_FOR_SHOW_TIME = BOOKING_DISCOUNT_APPLIER_ERROR_MESSAGE_PREFIX + "applicable.for.show.time";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("messages.pricing.calculator.BookingDiscountApplierMessages");
    }
}
