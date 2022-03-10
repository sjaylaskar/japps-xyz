/*
* Id: ShowTimeType.java 03-Mar-2022 11:42:52 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.model;

import java.time.LocalTime;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.xyz.apps.ticketeer.pricing.calculator.service.InvalidShowTimeTypeException;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;

import lombok.Getter;

/**
 * The show time type.
 */

/**
 * Gets the range end time.
 *
 * @return the range end time
 */
@Getter
public enum ShowTimeType {

    /** The morning. */
    MORNING(LocalDateTimeFormatUtil.parseLocalTime("06:00"), LocalDateTimeFormatUtil.parseLocalTime("11:59"), "Applicable for morning shows."),

    /** The afternoon. */
    AFTERNOON(LocalDateTimeFormatUtil.parseLocalTime("12:00"), LocalDateTimeFormatUtil.parseLocalTime("17:59"), "Applicable for afternoon shows."),

    /** The evening. */
    EVENING(LocalDateTimeFormatUtil.parseLocalTime("18:00"), LocalDateTimeFormatUtil.parseLocalTime("23:59"), "Applicable for evening shows."),

    /** The night. */
    NIGHT(LocalDateTimeFormatUtil.parseLocalTime("00:00"), LocalDateTimeFormatUtil.parseLocalTime("05:59"), "Applicable for night shows.");

    /** The range start time. */
    private LocalTime rangeStartTime;

    /** The range end time. */
    private LocalTime rangeEndTime;

    /** The description. */
    private String description;

    /**
     * Instantiates a new show time type.
     *
     * @param rangeStartTime the range start time
     * @param rangeEndTime the range end time
     * @param description the description
     */
    ShowTimeType(final LocalTime rangeStartTime, final LocalTime rangeEndTime, final String description) {
        this.rangeStartTime = rangeStartTime;
        this.rangeEndTime = rangeEndTime;
        this.description = description;
    }

    /**
     * Full description.
     *
     * @return the string
     */
    public String fullDescription() {
        return name() + " : " + description + " Shows in between times: " + rangeString();
    }

    /**
     * Checks if is in show time range.
     *
     * @param showTime the local time
     * @return true, if is in show time range
     */
    public boolean isInShowTimeRange(final LocalTime showTime) {
        return (showTime.compareTo(rangeStartTime) == 0 || showTime.isAfter(rangeStartTime))
        && (showTime.compareTo(rangeEndTime) == 0 || showTime.isBefore(rangeEndTime));
    }

    /**
     * Range string.
     *
     * @return the range string
     */
    public String rangeString() {
        return rangeStartTime + " and " + rangeEndTime;
    }

    /**
     * Of.
     *
     * @param showTimeType the show time type
     * @return the show time type
     */
    public static ShowTimeType of(final String showTimeType) {


        return Arrays.asList(values())
            .stream()
            .filter(value -> StringUtils.equalsIgnoreCase(value.name(), showTimeType))
            .findFirst()
            .orElseThrow(() -> new InvalidShowTimeTypeException(showTimeType));

    }
}
