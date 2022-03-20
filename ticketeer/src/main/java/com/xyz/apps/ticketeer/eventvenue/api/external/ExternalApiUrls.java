/*
* Id: ExternalApiUrls.java 14-Mar-2022 2:13:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.external;


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
    private static final String API_EXTERNAL_PREFIX = "eventvenue.api.external.";

    /** The get city by id. */
    public static final String GET_CITY_BY_ID = API_EXTERNAL_PREFIX + "city.getById";

    /** The get event by id. */
    public static final String GET_EVENT_BY_ID = API_EXTERNAL_PREFIX + "event.getById";

    /** The event details by id. */
    public static final String GET_EVENT_DETAILS_BY_ID = API_EXTERNAL_PREFIX + "event.details.getById";

}
