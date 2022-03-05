/*
* Id: WebClientBuilder.java 05-Mar-2022 1:33:22 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * The web client builder.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class WebClientBuilder {

    /**
     * Instantiates a new web client builder.
     */
    private WebClientBuilder() {

    }

    /** The instance. */
    private static final WebClientBuilder instance = new WebClientBuilder();

    /** The web client builder. */
    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Instance.
     *
     * @return the web client builder
     */
    private static WebClientBuilder instance() {
        return instance;
    }

    /**
     * Gets the.
     *
     * @return the web client. builder
     */
    public static WebClient.Builder get() {
        return instance().webClientBuilder;
    }
}
