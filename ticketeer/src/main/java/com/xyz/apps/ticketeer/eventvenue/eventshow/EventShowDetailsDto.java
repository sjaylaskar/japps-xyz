/*
* Id: EventShowDetailsDto.java 02-Mar-2022 3:06:56 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.SeatRowPriceDtoList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The EventShowDetailsDto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventShowDetailsDto {

    /** The event id. */
    private Long eventId;

    /** The event venue id. */
    private Long eventVenueId;

    /** The auditorium id. */
    private Long auditoriumId;

    /** The date. */
    private String date;

    /** The start time. */
    private String startTime;

    /** The end time. */
    private String endTime;

    /** The seat row price dto list. */
    private SeatRowPriceDtoList seatRowPriceDtoList = new SeatRowPriceDtoList();
}
