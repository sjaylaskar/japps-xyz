/*
* Id: ShowTimeType.java 03-Mar-2022 11:42:52 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator;

import java.time.LocalTime;

import lombok.Getter;

/**
 * The show time type.
 */
@Getter
public enum ShowTimeType {

    /** The morning. */
    MORNING(LocalTime.parse("06:00"), LocalTime.parse("11:59")),

    /** The afternoon. */
    AFTERNOON(LocalTime.parse("12:00"), LocalTime.parse("17:59")),

    /** The evening. */
    EVENING(LocalTime.parse("18:00"), LocalTime.parse("23:59")),

    /** The night. */
    NIGHT(LocalTime.parse("00:00"), LocalTime.parse("05:59"));

    /** The range start time. */
    private LocalTime rangeStartTime;

    /** The range end time. */
    private LocalTime rangeEndTime;

    /**
     * Instantiates a new show time type.
     *
     * @param rangeStartTime the range start time
     * @param rangeEndTime the range end time
     */
    ShowTimeType(final LocalTime rangeStartTime, final LocalTime rangeEndTime) {
        this.rangeStartTime = rangeStartTime;
        this.rangeEndTime = rangeEndTime;
    }

    /**
     * Checks if is in show time range.
     *
     * @param showTime the local time
     * @return true, if is in show time range
     */
    boolean isInShowTimeRange(final LocalTime showTime) {
        return (showTime.compareTo(rangeStartTime) == 0 || showTime.isAfter(rangeStartTime))
        && (showTime.compareTo(rangeEndTime) == 0 || showTime.isBefore(rangeEndTime));
    }

    /**
     * Range string.
     *
     * @return the range string
     */
    String rangeString() {
        return rangeStartTime + " and " + rangeEndTime;
    }
}
