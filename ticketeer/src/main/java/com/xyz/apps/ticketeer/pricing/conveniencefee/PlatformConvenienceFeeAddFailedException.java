/*
* Id: PlatformConvenienceFeeAddFailedException.java 05-Mar-2022 3:25:19 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee;


/**
 * The platform convenience fee add failed exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class PlatformConvenienceFeeAddFailedException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = 1496514246888966594L;

    public PlatformConvenienceFeeAddFailedException() {
        super("Failed to update the platform convenience fee.");
    }
}
