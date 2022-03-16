/*
 * Id: EventShowSeatInformationResponseDto.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.api.external.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show seat information response dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatInformationResponseDto {

    /** The id. */
    private Long id;

    /** The amount. */
    private Double amount;

    /** The seat status. */
    private String seatReservationStatus = SeatReservationStatus.AVAILABLE.name();

    /** The row name. */
    private String rowName;

    /** The seat number. */
    private String seatNumber;

    /** The reservation time. */
    private String reservationTime;

    /** The booking time. */
    private String bookingTime;

    /** The booking reservation id. */
    private String bookingReservationId;
}
