/*
* Id: BookingPriceInfo.java 04-Mar-2022 12:48:34 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.api.internal.contract;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.Dto;
import com.xyz.apps.ticketeer.pricing.calculator.model.BookingPriceInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The booking price info.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingPriceInfoDto extends Dto<BookingPriceInfo> {

    /** The seat base prices. */
    private List<Double> seatBasePrices = new ArrayList<>();

    /** The offer code. */
    private String offerCode;

    /** The city id. */
    private Long cityId;

    /** The event id. */
    private Long eventId;

    /** The event venue id. */
    private Long eventVenueId;

    /** The event show id. */
    private Long eventShowId;

    /** The show date. */
    private String showDate;

    /** The show start time. */
    private String showStartTime;

    /** The booking date. */
    private String bookingTime;

}
