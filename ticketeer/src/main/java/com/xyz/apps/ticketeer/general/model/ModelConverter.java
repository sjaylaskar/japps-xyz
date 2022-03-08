/*
* Id: ModelConverter.java 07-Mar-2022 2:02:53 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
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
    public static final Converter<LocalDate, String> LOCALDATE_TO_STRING_CONVERTER = converter -> (converter.getSource() != null) ? LocalDateTimeFormatUtil.format(converter.getSource()) : null;

    /** The localdatetime to string converter. */
    public static final Converter<LocalDateTime, String> LOCALDATETIME_TO_STRING_CONVERTER = converter -> (converter.getSource() != null) ? LocalDateTimeFormatUtil.format(converter.getSource()) : null;

    /** The localtime to string converter. */
    public static final Converter<LocalTime, String> LOCALTIME_TO_STRING_CONVERTER = converter -> (converter.getSource() != null) ? LocalDateTimeFormatUtil.format(converter.getSource()) : null;

    /** The string to localdate converter. */
    public static final Converter<String, LocalDate> STRING_TO_LOCALDATE_CONVERTER = converter -> (StringUtils.isNotBlank(converter.getSource())) ? LocalDateTimeFormatUtil.parseLocalDate(converter.getSource()) : null;

    /** The string to localdatetime converter. */
    public static final Converter<String, LocalDateTime> STRING_TO_LOCALDATETIME_CONVERTER = converter -> (StringUtils.isNotBlank(converter.getSource())) ? LocalDateTimeFormatUtil.parseLocalDateTime(converter.getSource()) : null;

    /** The string to localtime converter. */
    public static final Converter<String, LocalTime> STRING_TO_LOCALTIME_CONVERTER = converter -> (StringUtils.isNotBlank(converter.getSource())) ? LocalDateTimeFormatUtil.parseLocalTime(converter.getSource()) : null;

    /** The enum to name converter. */
    public static final Converter<Enum<?>, String> ENUM_TO_NAME_CONVERTER = converter -> (converter.getSource() != null) ? converter.getSource().name() : null;

    /** The string to object id converter. */
    public static final Converter<String, ObjectId> STRING_TO_OBJECTID_CONVERTER = converter -> (StringUtils.isNotBlank(converter.getSource())) ? new ObjectId(converter.getSource()) : null;

    /** The object id to string converter. */
    public static final Converter<ObjectId, String> OBJECTID_TO_STRING_CONVERTER = converter -> converter.getSource().toHexString();
}
