/*
* Id: SeatRowPriceDtoList.java 02-Mar-2022 3:37:20 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The SeatRowPriceDtoList.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatRowPriceDtoList {

    /** The seat row price dtos. */
    private List<SeatRowPriceDto> seatRowPriceDtos = new ArrayList<>();

    /**
     * Instantiates a new seat row price dto list.
     */
    public SeatRowPriceDtoList() {

    }

    public SeatRowPriceDtoList(final List<SeatRowPriceDto> seatRowPriceDtos) {
        this.seatRowPriceDtos.addAll(seatRowPriceDtos);
    }

}
