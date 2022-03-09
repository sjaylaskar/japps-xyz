/*
* Id: EventShowSeat.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show seat.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatDetailsDto {

    /** The id. */
    private Long id;

    /** The amount. */
    private Double amount;

    /** The seat reservation status. */
    private String seatReservationStatus = SeatReservationStatus.AVAILABLE.name();

    /** The seat number. */
    private String seatNumber;

    /**
     * Of.
     *
     * @param id the id
     * @param amount the amount
     * @param seatReservationStatus the seat reservation status
     * @param seatNumber the seat number
     * @return the event show seat details dto
     */
    public static EventShowSeatDetailsDto of(final Long id, final Double amount, final String seatReservationStatus, final String seatNumber) {
        final EventShowSeatDetailsDto eventShowSeatDetailsDto = new EventShowSeatDetailsDto();
        eventShowSeatDetailsDto.setId(id);
        eventShowSeatDetailsDto.setAmount(amount);
        eventShowSeatDetailsDto.setSeatReservationStatus(seatReservationStatus);
        eventShowSeatDetailsDto.setSeatNumber(seatNumber);
        return eventShowSeatDetailsDto;
    }

}
