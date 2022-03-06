/*
* Id: SeatStatus.java 02-Mar-2022 2:31:13 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * The seat reservation status.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum SeatReservationStatus {

    /** The available. */
    AVAILABLE,

    /** The reserved. */
    RESERVED,

    /** The booked. */
    BOOKED;

    /**
     * Of.
     *
     * @param status the status
     * @return the seat reservation status
     */
    public static SeatReservationStatus of(final String status) {
        return Arrays.asList(values()).stream()
        .filter(value -> StringUtils.equalsIgnoreCase(value.name(), status))
        .findFirst()
        .orElseThrow(() -> new InvalidSeatReservationStatusException(status));
    }
}
