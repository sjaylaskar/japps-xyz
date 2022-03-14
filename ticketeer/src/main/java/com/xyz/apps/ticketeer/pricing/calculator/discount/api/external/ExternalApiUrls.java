/*
 * Id: ExternalApiUrls.java 14-Mar-2022 12:34:58 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.api.external;

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
    private static final String API_EXTERNAL_PREFIX = "pricing.calculator.discount.api.external.";

    /** The get city by id. */
    public static final String GET_CITY_BY_ID = API_EXTERNAL_PREFIX + "city.getById";

    /** The get event venue by id. */
    public static final String GET_EVENT_VENUE_BY_ID = API_EXTERNAL_PREFIX + "eventvenue.getById";
}
