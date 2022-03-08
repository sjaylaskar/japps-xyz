/*
* Id: PlatformConvenienceFeeNotFoundException.java 05-Mar-2022 4:22:52 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee;


/**
 * The platform convenience fee not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class PlatformConvenienceFeeNotFoundException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = -3078648203488004449L;

    /**
     * Instantiates a new platform convenience fee not found exception.
     */
    public PlatformConvenienceFeeNotFoundException() {
        super("The platform convenience fee is not found.");
    }

    /**
     * Instantiates a new platform convenience fee not found exception.
     *
     * @param id the id
     */
    public PlatformConvenienceFeeNotFoundException(final String id) {
        super("The platform convenience fee with id: " + id +  " is not found.");
    }
}
