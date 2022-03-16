/*
* Id: EnvironmentDefaults.java 16-Mar-2022 4:38:45 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.resources;


/**
 * The environment defaults.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class EnvironmentDefaults {

    /**
     * Instantiates a new environment defaults.
     */
    private EnvironmentDefaults() {

    }

    /** The show endtime duration extra minutes. */
    public static final long SHOW_ENDTIME_DURATION_EXTRA_MINUTES = 30;

    /** The max seats per booking. */
    public static final int MAX_SEATS_PER_BOOKING = 6;

    /** The seat reservation expiry time minutes. */
    public static final long SEAT_RESERVATION_EXPIRY_TIME_MINUTES = 5;

}
