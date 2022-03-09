/*
* Id: UserDtoList.java 07-Mar-2022 3:42:53 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.internal.contract;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The booking details dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDetailsDtoList {

    /** The booking details dtos. */
    private List<BookingDetailsDto> bookings = new ArrayList<>();

    /**
     * Instantiates a new booking details dto list.
     */
    BookingDetailsDtoList() {

    }

    /**
     * Instantiates a new booking details dto list.
     *
     * @param bookings the booking details dtos
     */
    BookingDetailsDtoList(final List<BookingDetailsDto> bookings) {
        this.bookings.addAll(bookings);
    }
}
