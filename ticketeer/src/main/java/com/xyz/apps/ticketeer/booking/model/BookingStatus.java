/*
* Id: BookingStatus.java 02-Mar-2022 6:05:53 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.model;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.xyz.apps.ticketeer.booking.service.InvalidBookingStatusException;

/**
 * The BookingStatus.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum BookingStatus {

    RESERVED,
    CONFIRMED,
    CANCELLED;

    /**
     * Of.
     *
     * @param bookingStatus the booking status
     * @return the booking status
     */
    public static BookingStatus of(final String bookingStatus) {
        return
        Arrays.asList(values())
        .stream()
        .filter(value -> StringUtils.equalsIgnoreCase(value.name(), bookingStatus))
        .findFirst()
        .orElseThrow(() -> new InvalidBookingStatusException(bookingStatus));
    }
}
