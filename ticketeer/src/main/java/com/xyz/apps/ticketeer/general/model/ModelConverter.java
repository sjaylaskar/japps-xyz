/*
* Id: ModelConverter.java 07-Mar-2022 2:02:53 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.modelmapper.Converter;

import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;

/**
 * The model converter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class ModelConverter {

    /**
     * Instantiates a new model converter.
     */
    private ModelConverter() {

    }

    /** The localdate to string converter. */
    public static final Converter<LocalDate, String> LOCALDATE_TO_STRING_CONVERTER = converter -> LocalDateTimeFormatUtil.format(converter.getSource());

    /** The localdatetime to string converter. */
    public static final Converter<LocalDateTime, String> LOCALDATETIME_TO_STRING_CONVERTER = converter -> LocalDateTimeFormatUtil.format(converter.getSource());

    /** The localtime to string converter. */
    public static final Converter<LocalTime, String> LOCALTIME_TO_STRING_CONVERTER = converter -> LocalDateTimeFormatUtil.format(converter.getSource());

    /** The string to localdate converter. */
    public static final Converter<String, LocalDate> STRING_TO_LOCALDATE_CONVERTER = converter -> LocalDateTimeFormatUtil.parseLocalDate(converter.getSource());

    /** The string to localdatetime converter. */
    public static final Converter<String, LocalDateTime> STRING_TO_LOCALDATETIME_CONVERTER = converter -> LocalDateTimeFormatUtil.parseLocalDateTime(converter.getSource());

    /** The string to localtime converter. */
    public static final Converter<String, LocalTime> STRING_TO_LOCALTIME_CONVERTER = converter -> LocalDateTimeFormatUtil.parseLocalTime(converter.getSource());

    /** The enum to name converter. */
    public static final Converter<Enum<?>, String> ENUM_TO_NAME_CONVERTER = converter -> converter.getSource().name();
}