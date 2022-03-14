/*
* Id: PlatformConvenienceFeeAddFailedException.java 05-Mar-2022 3:25:19 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.service;

import com.xyz.apps.ticketeer.pricing.conveniencefee.resources.Messages;

/**
 * The platform convenience fee update failed exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class PlatformConvenienceFeeUpdateFailedException extends PlatformConvenienceFeeServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 1496514246888966594L;

    /**
     * Instantiates a new platform convenience fee update failed exception.
     */
    public PlatformConvenienceFeeUpdateFailedException() {
        super(Messages.MESSAGE_ERROR_FAILURE_UPDATE_PLATFORM_CONVENIENCE_FEE);
    }
}
