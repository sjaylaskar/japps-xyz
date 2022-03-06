/*
* Id: BookingPriceInfoModelMapper.java 06-Mar-2022 8:52:13 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.general.AbstractModelMapper;


/**
 * The booking price info model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class BookingPriceInfoModelMapper extends AbstractModelMapper<BookingPriceInfo, BookingPriceInfoDto> {

    /**
     * Instantiates a new booking price info model mapper.
     */
    public BookingPriceInfoModelMapper() {
        super(BookingPriceInfo.class, BookingPriceInfoDto.class);

        initMappings();
    }

    /**
     * Initializes the mappings.
     */
    private void initMappings() {

        final TypeMap<BookingPriceInfo, BookingPriceInfoDto> bookingPriceInfoToBookingPriceInfoDtoMap = modelMapper.createTypeMap(BookingPriceInfo.class, BookingPriceInfoDto.class);
        bookingPriceInfoToBookingPriceInfoDtoMap
        .addMappings(
          mapper -> mapper.map(bookingPriceInfo -> bookingPriceInfo.getBookingTime().toString(), BookingPriceInfoDto::setBookingTime)
        )
        .addMappings(
            mapper -> mapper.map(bookingPriceInfo -> bookingPriceInfo.getShowStartTime().toString(), BookingPriceInfoDto::setShowStartTime)
          );

        final TypeMap<BookingPriceInfoDto, BookingPriceInfo> eventDetailsDtoToEventDetailsMap = modelMapper.createTypeMap(BookingPriceInfoDto.class, BookingPriceInfo.class);
        eventDetailsDtoToEventDetailsMap
        .addMappings(
          mapper -> mapper.map(bookingPriceInfoDto -> LocalDateTime.parse(bookingPriceInfoDto.getBookingTime()), BookingPriceInfo::setBookingTime)
        )
        .addMappings(
            mapper -> mapper.map(bookingPriceInfoDto -> LocalTime.parse(bookingPriceInfoDto.getShowStartTime()), BookingPriceInfo::setShowStartTime)
        );
    }
}
