/*
* Id: BookingPriceInfoModelMapper.java 06-Mar-2022 8:52:13 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator;

import javax.annotation.PostConstruct;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;


/**
 * The booking price info model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class BookingPriceInfoModelMapper extends GeneralModelMapper<BookingPriceInfo, BookingPriceInfoDto> {

    /**
     * Instantiates a new booking price info model mapper.
     */
    public BookingPriceInfoModelMapper() {
        super(BookingPriceInfo.class, BookingPriceInfoDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<BookingPriceInfo, BookingPriceInfoDto> bookingPriceInfoToBookingPriceInfoDtoMap = modelMapper.createTypeMap(BookingPriceInfo.class, BookingPriceInfoDto.class);
        bookingPriceInfoToBookingPriceInfoDtoMap
        .addMappings(
          mapper -> mapper.using(ModelConverter.LOCALDATETIME_TO_STRING_CONVERTER).map(BookingPriceInfo::getBookingTime, BookingPriceInfoDto::setBookingTime)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALTIME_TO_STRING_CONVERTER).map(BookingPriceInfo::getShowStartTime, BookingPriceInfoDto::setShowStartTime)
          );

        final TypeMap<BookingPriceInfoDto, BookingPriceInfo> eventDetailsDtoToEventDetailsMap = modelMapper.createTypeMap(BookingPriceInfoDto.class, BookingPriceInfo.class);
        eventDetailsDtoToEventDetailsMap
        .addMappings(
          mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATETIME_CONVERTER).map(BookingPriceInfoDto::getBookingTime, BookingPriceInfo::setBookingTime)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALTIME_CONVERTER).map(BookingPriceInfoDto::getShowStartTime, BookingPriceInfo::setShowStartTime)
        );
    }
}
