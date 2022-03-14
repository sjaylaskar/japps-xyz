/*
* Id: PlatformConvenienceFeeServiceException.java 13-Mar-2022 1:02:51 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.service;

import com.xyz.apps.ticketeer.general.service.ServiceException;
import com.xyz.apps.ticketeer.pricing.conveniencefee.resources.Messages;

/**
 * The platform convenience fee service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class PlatformConvenienceFeeServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 1946604874759038374L;

    /**
     * Instantiates a new platform convenience fee service exception.
     *
     * @param message the message
     */
    public PlatformConvenienceFeeServiceException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

}
