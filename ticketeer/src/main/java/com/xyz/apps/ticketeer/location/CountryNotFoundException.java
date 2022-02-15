/*
* Id: CountryNotFoundException.java 15-Feb-2022 4:57:58 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;


/**
 * The country not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CountryNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -2299273285227837285L;

    /**
     * Instantiates a new country not found exception.
     */
    public CountryNotFoundException() {
        super();
    }

    /**
     * Instantiates a new country not found exception.
     *
     * @param countryId the country id
     */
    public CountryNotFoundException(final Long countryId) {
        super("Country not found for id: " + countryId);
    }

}
