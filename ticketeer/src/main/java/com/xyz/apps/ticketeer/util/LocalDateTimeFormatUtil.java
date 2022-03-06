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

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * The local date time format util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Validated
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
    public static String format(@NotNull(message = "The localDate must not be null.") final LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Format.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String format(@NotNull(message = "The localDate must not be null.") final LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Format.
     *
     * @param localTime the local time
     * @return the string
     */
    public static String format(@NotNull(message = "The localDate must not be null.") final LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    /**
     * Parses the local date.
     *
     * @param localDateString the local date string
     * @return the local date
     */
    public static LocalDate parseLocalDate(@NotNull(message = "The localDateString must not be null.") final String localDateString) {
        return LocalDate.parse(localDateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Parses the local date time.
     *
     * @param localDateTimeString the local date time string
     * @return the local date time
     */
    public static LocalDateTime parseLocalDateTime(@NotNull(message = "The localDateTimeString must not be null.") final String localDateTimeString) {
        return LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Parses the local time.
     *
     * @param localTimeString the local time string
     * @return the local time
     */
    public static LocalTime parseLocalTime(@NotNull(message = "The localTimeString must not be null.") final String localTimeString) {
        return LocalTime.parse(localTimeString, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
