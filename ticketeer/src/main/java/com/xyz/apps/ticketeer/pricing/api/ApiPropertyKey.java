/*
* Id: ApiPropertyKey.java 05-Mar-2022 12:59:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.api;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;

/**
 * The api property key.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum ApiPropertyKey {

    /** The get city by id. */
    GET_CITY_BY_ID("api.city.getById"),

    /** The get event venue by id. */
    GET_EVENT_VENUE_BY_ID("api.eventvenue.getById");

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