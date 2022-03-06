/*
* Id: SeatRowPriceDto.java 02-Mar-2022 3:35:47 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The SeatRowPriceDto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class SeatRowPriceDto {

    /** The start row. */
    private char startRow;

    /** The end row. */
    private char endRow;

    /** The amount. */
    private Double amount;
}
