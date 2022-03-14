/*
* Id: PlatformConvenienceFeeNotFoundException.java 05-Mar-2022 4:22:52 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.service;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.pricing.conveniencefee.resources.Messages;

/**
 * The platform convenience fee not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class PlatformConvenienceFeeNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -3078648203488004449L;

    /**
     * Instantiates a new platform convenience fee not found exception.
     */
    public PlatformConvenienceFeeNotFoundException() {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_PLATFORM_CONVENIENCE_FEE);
    }

    /**
     * Instantiates a new platform convenience fee not found exception.
     *
     * @param id the id
     */
    public PlatformConvenienceFeeNotFoundException(final String id) {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_PLATFORM_CONVENIENCE_FEE_ID, id);
    }
}
