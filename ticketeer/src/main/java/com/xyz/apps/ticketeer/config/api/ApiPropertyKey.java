/*
* Id: ApiKey.java 05-Mar-2022 1:28:01 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.config.api;


/**
 * The api property key.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum ApiPropertyKey {

    /** The base url. */
    BASE_URL("api.baseUrl");

    /** The key. */
    private String key;

    /**
     * Instantiates a new api property key.
     *
     * @param key the key
     */
    ApiPropertyKey(final String key) {
        this.key = key;
    }

    /**
     * Gets the.
     *
     * @return the string
     */
    public String get() {
        return key;
    }
}
