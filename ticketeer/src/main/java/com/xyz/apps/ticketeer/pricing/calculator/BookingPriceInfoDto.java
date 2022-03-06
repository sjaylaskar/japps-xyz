/*
* Id: BookingPriceInfo.java 04-Mar-2022 12:48:34 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator;

import com.xyz.apps.ticketeer.model.general.Dto;

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
public class BookingPriceInfoDto extends Dto<BookingPriceInfo> {

    /** The offer code. */
    private String offerCode;

    /** The base amount. */
    private Double baseAmount;

    /** The number of seats. */
    private Integer numberOfSeats;

    /** The city id. */
    private Long cityId;

    /** The event venue id. */
    private Long eventVenueId;

    /** The show start time. */
    private String showStartTime;

    /** The final amount. */
    private Double finalAmount;

    /** The booking date. */
    private String bookingTime;

}
