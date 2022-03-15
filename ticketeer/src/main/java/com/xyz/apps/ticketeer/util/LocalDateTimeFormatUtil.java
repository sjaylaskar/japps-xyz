/*
 * Id: LocalDateTimeFormatUtil.java 06-Mar-2022 10:00:19 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**
 * The local date time format util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class LocalDateTimeFormatUtil {

    /**
     * Instantiates a new local date time format util.
     */
    private LocalDateTimeFormatUtil() {

    }

    /**
     * Format.
     *
     * @param localDate the local date
     * @return the string
     */
    public static String format(final LocalDate localDate) {

        Objects.requireNonNull(localDate, "The date cannot be null.");
        try {
            return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (final Exception exception) {
            throw new IllegalArgumentException("Invalid date: "
                + localDate + " for format: " + DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    /**
     * Format.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String format(final LocalDateTime localDateTime) {

        Objects.requireNonNull(localDateTime, "The date-time cannot be null.");
        try {
            return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (final Exception exception) {
            throw new IllegalArgumentException("Invalid date-time: "
                + localDateTime + " for format: " + DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    /**
     * Format.
     *
     * @param localTime the local time
     * @return the string
     */
    public static String format(final LocalTime localTime) {

        Objects.requireNonNull(localTime, "The time cannot be null.");
        try {
            return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (final Exception exception) {
            throw new IllegalArgumentException("Invalid time: "
                + localTime + " for format: " + DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    /**
     * Parses the local date.
     *
     * @param localDateString the local date string
     * @return the local date
     */
    public static LocalDate parseLocalDate(final String localDateString) {

        Objects.requireNonNull(localDateString, "The date cannot be null.");
        try {
            return LocalDate.parse(localDateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (final Exception exception) {
            throw new IllegalArgumentException("Invalid date string: "
                + localDateString + " for format: " + DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    /**
     * Parses the local date time.
     *
     * @param localDateTimeString the local date time string
     * @return the local date time
     */
    public static LocalDateTime parseLocalDateTime(final String localDateTimeString) {

        Objects.requireNonNull(localDateTimeString, "The date-time cannot be null.");
        try {
            return LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (final Exception exception) {
            throw new IllegalArgumentException("Invalid date time string: "
                + localDateTimeString + " for format: " + DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    /**
     * Parses the local time.
     *
     * @param localTimeString the local time string
     * @return the local time
     */
    public static LocalTime parseLocalTime(final String localTimeString) {

        Objects.requireNonNull(localTimeString, "The time cannot be null.");
        try {
            return LocalTime.parse(localTimeString, DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (final Exception exception) {
            throw new IllegalArgumentException("Invalid time string: "
                + localTimeString + " for format: " + DateTimeFormatter.ISO_LOCAL_DATE);
        }

    }
}
