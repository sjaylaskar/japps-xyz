/*
* Id: ApiPropertyKey.java 05-Mar-2022 12:59:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.api.external;

/**
 * The api property key.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum ApiPropertyKey {

    /** The get city by id. */
    GET_EVENT_SHOWS_BY_CITY_ID("api.external.eventshow.city.getByCityId/{0}");

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