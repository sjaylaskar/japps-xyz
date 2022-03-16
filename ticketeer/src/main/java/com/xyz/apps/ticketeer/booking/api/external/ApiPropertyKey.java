/*
* Id: ApiPropertyKey.java 05-Mar-2022 12:59:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external;

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

    /** The event show seats reserve. */
    EVENT_SHOW_SEATS_RESERVE("api.external.eventshow.seats.reserve"),

    /** The event show seats book. */
    EVENT_SHOW_SEATS_BOOK("api.external.eventshow.seats.book"),

    /** The event show seats cancel. */
    EVENT_SHOW_SEATS_CANCEL("api.external.eventshow.seats.cancel"),

    /** The pricing calculate. */
    PRICING_CALCULATE("api.external.pricing.calculate"),

    /** The event show detailed info. */
    EVENT_SHOW_DETAILED_INFO("api.external.eventshow.details.getById"),

    /** The event show seats by event show id. */
    EVENT_SHOW_SEATS_BY_EVENT_SHOW_ID("api.external.eventshow.seats.reservation.seats-for-show"),

    /** The event show seat prices. */
    EVENT_SHOW_SEAT_PRICES("api.external.eventshow.seats.reservation.seat-prices"),

    /** The event show seats by reservation id. */
    EVENT_SHOW_SEATS_BY_RESERVATION_ID("api.external.eventshow.seats.reservation.seats-by-reservation");

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
}
