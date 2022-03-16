/*
 * Id: EnvironmentProperties.java 16-Mar-2022 4:51:16 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.general.resources;

import org.springframework.core.env.Environment;

import lombok.extern.log4j.Log4j2;


/**
 * The environment properties.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */

/** The log. */

/** The log. */
@Log4j2
public final class EnvironmentProperties {

    /** The instance. */
    private static EnvironmentProperties instance;

    /** The environment. */
    private Environment environment;

    /**
     * Instantiates a new environment property keys.
     *
     * @param environment the environment
     */
    private EnvironmentProperties(final Environment environment) {

        this.environment = environment;
    }

    /**
     * Gets the environment properties.
     *
     * @param environment the environment
     * @return the environment properties
     */
    public static EnvironmentProperties get(final Environment environment) {

        if (instance == null) {
            instance = new EnvironmentProperties(environment);
        }
        return instance;
    }

    /** The using default text. */
    private static final String USING_DEFAULT_TEXT = "Resorting to default value.";

    /** The show endtime duration extra minutes. */
    private static final String SHOW_ENDTIME_DURATION_EXTRA_MINUTES = "show.endtime.duration.extra.minutes";

    /** The max seats per booking. */
    private static final String MAX_SEATS_PER_BOOKING = "max.seats.per.booking";

    /** The seat reservation expiry time minutes. */
    private static final String SEAT_RESERVATION_EXPIRY_TIME_MINUTES = "seat.reservation.expiry.time.minutes";

    /**
     * Show end time duration extra minutes.
     *
     * @return the show end time duration extra minutes.
     */
    public long showEndTimeDurationExtraMinutes() {

        try {
            return environment.getProperty(SHOW_ENDTIME_DURATION_EXTRA_MINUTES, Long.class,
                EnvironmentDefaults.SHOW_ENDTIME_DURATION_EXTRA_MINUTES);
        } catch (final Exception exception) {
            logException(exception);
            return EnvironmentDefaults.SHOW_ENDTIME_DURATION_EXTRA_MINUTES;
        }
    }

    /**
     * Max seats per booking.
     *
     * @return the max seats per booking.
     */
    public int maxSeatsPerBooking() {

        try {
            return environment.getProperty(MAX_SEATS_PER_BOOKING, Integer.class, EnvironmentDefaults.MAX_SEATS_PER_BOOKING);
        } catch (final Exception exception) {
            logException(exception);
            return EnvironmentDefaults.MAX_SEATS_PER_BOOKING;
        }
    }

    /**
     * Seat reservation expiry time minutes.
     *
     * @return the seat reservation expiry time minutes.
     */
    public long seatReservationExpiryTimeMinutes() {

        try {
            return environment.getProperty(SEAT_RESERVATION_EXPIRY_TIME_MINUTES, Long.class,
                EnvironmentDefaults.SEAT_RESERVATION_EXPIRY_TIME_MINUTES);
        } catch (final Exception exception) {
            logException(exception);
            return EnvironmentDefaults.SEAT_RESERVATION_EXPIRY_TIME_MINUTES;
        }
    }

    /**
     * Log exception.
     *
     * @param exception the exception
     */
    private void logException(final Exception exception) {

        log.error(exception.getLocalizedMessage());
        log.warn(USING_DEFAULT_TEXT);
    }
}
