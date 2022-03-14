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

    /** The offer code. */
    private String offerCode;

    /** The base amount. */
    private Double baseAmount;

    /** The seat prices. */
    private List<Double> seatPrices = new ArrayList<>();

    /** The number of seats. */
    private Integer numberOfSeats;

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

    /** The final amount. */
    private Double finalAmount;

    /** The booking date. */
    private String bookingTime;

}
