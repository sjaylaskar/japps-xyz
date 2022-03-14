/*
* Id: ExternalApiUrls.java 14-Mar-2022 2:13:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.api.external;


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
    private static final String API_EXTERNAL_PREFIX = "event.api.external.";

    /** The get event shows by city id. */
    public static final String GET_EVENT_SHOWS_BY_CITY_ID = API_EXTERNAL_PREFIX + "eventshow.city.getByCityId";
}
