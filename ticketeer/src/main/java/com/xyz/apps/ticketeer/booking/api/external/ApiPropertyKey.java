/*
* Id: ApiPropertyKey.java 05-Mar-2022 12:59:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;

/**
 * The api property key.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum ApiPropertyKey {

    /** The authenticate user. */
    AUTHENTICATE_USER("api.external.user.authenticate"),

    /** The get city by id. */
    GET_CITY_BY_ID("api.external.city.getById"),

    /** The get event show by id. */
    GET_EVENT_SHOW_BY_ID("api.external.eventshow.getById"),

    /** The calculate event show seats amount. */
    CALCULATE_EVENT_SHOW_SEATS_AMOUNT("api.external.eventshow.seats.amount.calculate"),

    /** The event show seats are available. */
    EVENT_SHOW_SEATS_ARE_AVAILABLE("api.external.eventshow.seats.are.available"),

    /** The event show seats are reserved. */
    EVENT_SHOW_SEATS_ARE_RESERVED("api.external.eventshow.seats.are.reserved"),

    /** The event show seats reserve. */
    EVENT_SHOW_SEATS_RESERVE("api.external.eventshow.seats.reserve"),

    /** The event show seats unreserve. */
    EVENT_SHOW_SEATS_UNRESERVE("api.external.eventshow.seats.unreserve"),

    /** The event show seats book. */
    EVENT_SHOW_SEATS_BOOK("api.external.eventshow.seats.book"),

    /** The event show seats reserve with booking. */
    EVENT_SHOW_SEATS_RESERVE_WITH_BOOKING("api.external.eventshow.seats.reserve.with.booking"),

    /** The event show seats cancel. */
    EVENT_SHOW_SEATS_CANCEL("api.external.eventshow.seats.cancel"),

    /** The pricing calculate. */
    PRICING_CALCULATE("api.external.pricing.calculate");

    /** The key. */
    private String key;

    /**
     * Instantiates a new api property key.
     *
     * @param key the key
     */
    private ApiPropertyKey(final String key) {

        this.key = key;
    }

    /**
     * Gets the url.
     *
     * @return the string
     */
    public String get() {
        return key;
    }

    /**
     * Gets the url replacing all placeholders.
     *
     * @param placeHolderValues the place holder values
     * @return the string
     */
    public String get(final Object ...placeHolderValues) {
        return (ArrayUtils.isNotEmpty(placeHolderValues))
                ? MessageFormat.format(get(), placeHolderValues)
                : get();
    }
}
