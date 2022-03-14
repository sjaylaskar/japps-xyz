/*
* Id: ExternalApiUrls.java 14-Mar-2022 2:13:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.api.external;


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
    private static final String API_EXTERNAL_PREFIX = "pricing.calculator.api.external.";

    /** The get platform convenience fee percentage. */
    public static final String GET_PLATFORM_CONVENIENCE_FEE_PERCENTAGE = API_EXTERNAL_PREFIX + "conveniencefee.percentage";
}
