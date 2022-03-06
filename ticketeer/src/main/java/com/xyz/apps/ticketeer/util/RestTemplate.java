/*
* Id: RestTemplate.java 05-Mar-2022 1:52:41 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The rest template.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component(value = "appRestTemplate")
public final class RestTemplate {

    /**
     * Instantiates a new rest template.
     */
    private RestTemplate() {

    }

    /** The instance. */
    private static final RestTemplate instance = new RestTemplate();

    /** The rest template. */
    @Autowired
    private org.springframework.web.client.RestTemplate restTemplate;

    /**
     * Instance.
     *
     * @return the rest template
     */
    private static RestTemplate instance() {
        return instance;
    }

    /**
     * Gets the spring data rest template.
     *
     * @return the org.springframework.data.restdb.core. rest template
     */
    public static org.springframework.web.client.RestTemplate get() {
        return instance().restTemplate;
    }
}
