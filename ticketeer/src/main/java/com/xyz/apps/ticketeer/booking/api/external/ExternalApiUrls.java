/*
* Id: ExternalApiUrls.java 14-Mar-2022 2:13:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external;


/**
 * The external api urls.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class ExternalApiUrls {

    /**
     * Instantiates a new external api urls.
     */
    private ExternalApiUrls() {

    }

    /** The api external prefix. */
    private static final String API_EXTERNAL_PREFIX = "booking.api.external.";

    /** The authenticate user. */
    public static final String AUTHENTICATE_USER = API_EXTERNAL_PREFIX + "user.authenticate";

    /** The get city by id. */
    public static final String GET_CITY_BY_ID = API_EXTERNAL_PREFIX + "city.getById";

    /** The get event show by id. */
    public static final String GET_EVENT_SHOW_BY_ID = API_EXTERNAL_PREFIX + "eventshow.getById";

    /** The event show seats reserve. */
    public static final String EVENT_SHOW_SEATS_RESERVE = API_EXTERNAL_PREFIX + "eventshow.seats.reserve";

    /** The event show seats book. */
    public static final String EVENT_SHOW_SEATS_BOOK = API_EXTERNAL_PREFIX + "eventshow.seats.book";

    /** The event show seats cancel. */
    public static final String EVENT_SHOW_SEATS_CANCEL = API_EXTERNAL_PREFIX + "eventshow.seats.cancel";

    /** The pricing calculate. */
    public static final String PRICING_CALCULATE = API_EXTERNAL_PREFIX + "pricing.calculate";

    /** The event show detailed info. */
    public static final String EVENT_SHOW_DETAILED_INFO = API_EXTERNAL_PREFIX + "eventshow.details.getById";

    /** The event show seats by event show id. */
    public static final String EVENT_SHOW_SEATS_BY_EVENT_SHOW_ID = API_EXTERNAL_PREFIX + "eventshow.seats.reservation.seats-for-show";

    /** The event show seat prices. */
    public static final String EVENT_SHOW_SEAT_PRICES = API_EXTERNAL_PREFIX + "eventshow.seats.reservation.seat-prices";

    /** The event show seats by reservation id. */
    public static final String EVENT_SHOW_SEATS_BY_RESERVATION_ID = API_EXTERNAL_PREFIX + "eventshow.seats.reservation.seats-by-reservation";
}
