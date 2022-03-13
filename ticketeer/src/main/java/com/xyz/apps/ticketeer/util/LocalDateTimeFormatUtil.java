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
        Objects.requireNonNull(localDate, "The parameter localDate cannot be null.");
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Format.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String format(final LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "The parameter localDateTime cannot be null.");
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Format.
     *
     * @param localTime the local time
     * @return the string
     */
    public static String format(final LocalTime localTime) {
        Objects.requireNonNull(localTime, "The parameter localTime cannot be null.");
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    /**
     * Parses the local date.
     *
     * @param localDateString the local date string
     * @return the local date
     */
    public static LocalDate parseLocalDate(final String localDateString) {
        Objects.requireNonNull(localDateString, "The parameter localDateString cannot be null.");
        return LocalDate.parse(localDateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Parses the local date time.
     *
     * @param localDateTimeString the local date time string
     * @return the local date time
     */
    public static LocalDateTime parseLocalDateTime(final String localDateTimeString) {
        Objects.requireNonNull(localDateTimeString, "The parameter localDateTimeString cannot be null.");
        return LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Parses the local time.
     *
     * @param localTimeString the local time string
     * @return the local time
     */
    public static LocalTime parseLocalTime(final String localTimeString) {
        Objects.requireNonNull(localTimeString, "The parameter localTimeString cannot be null.");
        return LocalTime.parse(localTimeString, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
