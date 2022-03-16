/*
 * Id: EventShowSeatForShowResponseDto.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.api.external.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show seat for show response dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatForShowResponseDto {

    /** The id. */
    private Long id;

    /** The amount. */
    private Double amount;

    /** The seat status. */
    private String seatReservationStatus = "AVAILABLE";

    /** The row name. */
    private String rowName;

    /** The seat number. */
    private String seatNumber;

}
