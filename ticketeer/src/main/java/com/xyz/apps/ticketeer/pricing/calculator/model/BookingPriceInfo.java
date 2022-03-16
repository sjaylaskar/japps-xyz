/*
* Id: BookingPriceInfo.java 04-Mar-2022 12:48:34 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.xyz.apps.ticketeer.general.model.AbstractModel;

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
public class BookingPriceInfo extends AbstractModel {

    /** The seat prices. */
    private List<Double> seatPrices = new ArrayList<>();

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
    private LocalDate showDate;

    /** The show start time. */
    private LocalTime showStartTime;

    /** The booking date. */
    private LocalDateTime bookingTime;

    /** The base amount. */
    private Double baseAmount = 0d;

    /** The final amount. */
    private Double finalAmount = 0d;

}
