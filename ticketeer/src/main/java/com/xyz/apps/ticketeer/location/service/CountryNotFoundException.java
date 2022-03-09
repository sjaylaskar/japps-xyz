/*
* Id: CountryNotFoundException.java 15-Feb-2022 4:57:58 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location.service;


/**
 * The country not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CountryNotFoundException extends CountryServiceException {

    /**
     *
     */
    private static final long serialVersionUID = -2299273285227837285L;

    /**
     * Instantiates a new country not found exception.
     *
     * @param countryId the country id
     */
    public CountryNotFoundException(final Long countryId) {
        super("Country not found for id: " + countryId);
    }

    /**
     * Instantiates a new country not found exception.
     *
     * @param countryCodeOrName the country code
     */
    public CountryNotFoundException(final String countryCodeOrName) {
        super("Country not found: " + countryCodeOrName);
    }

}
