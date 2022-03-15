/*
* Id: ApiPropertyKey.java 05-Mar-2022 12:59:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.external;

/**
 * The api property key.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum ApiPropertyKey {

    /** The get city by id. */
    GET_CITY_BY_ID("api.external.city.getById"),

    /** The get city by id. */
    GET_EVENT_BY_ID("api.external.event.getById"),

    /** The get event details by event id. */
    GET_EVENT_DETAILS_BY_EVENT_ID("api.external.event.details.getById");

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
