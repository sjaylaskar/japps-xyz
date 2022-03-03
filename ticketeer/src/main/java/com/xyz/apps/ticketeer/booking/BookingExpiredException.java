/*
* Id: BookingExpiredException.java 03-Mar-2022 5:23:16 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking;


/**
 * The booking expired exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class BookingExpiredException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 565452579858754148L;

    public BookingExpiredException() {
        super("The booking has expired.");
    }

}