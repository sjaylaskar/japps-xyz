/*
* Id: EventShowSeatModificationResponseDto.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeat;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus;
import com.xyz.apps.ticketeer.general.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show seat modification response dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatModificationResponseDto extends Dto<EventShowSeat> {

    /** The id. */
    private Long id;

    /** The amount. */
    private Double amount;

    /** The seat reservation status. */
    private String seatReservationStatus = SeatReservationStatus.AVAILABLE.name();

    /** The event show. */
    private Long eventShowId;

    /** The seat row name. */
    private String rowName;

    /** The seat number. */
    private String seatNumber;
}
