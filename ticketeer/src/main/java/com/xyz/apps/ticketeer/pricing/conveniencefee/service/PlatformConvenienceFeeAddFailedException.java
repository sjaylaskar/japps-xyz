/*
* Id: PlatformConvenienceFeeAddFailedException.java 05-Mar-2022 3:25:19 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.service;

import org.springframework.context.MessageSource;

import com.xyz.apps.ticketeer.pricing.conveniencefee.resources.Messages;
import com.xyz.apps.ticketeer.util.MessageUtil;

/**
 * The platform convenience fee add failed exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class PlatformConvenienceFeeAddFailedException extends PlatformConvenienceFeeServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 1496514246888966594L;

    /**
     * Instantiates a new platform convenience fee add failed exception.
     */
    public PlatformConvenienceFeeAddFailedException(final MessageSource messageSource) {
        super(MessageUtil.defaultLocaleMessage(messageSource, Messages.MESSAGE_ERROR_FAILURE_ADD_PLATFORM_CONVENIENCE_FEE, null));
    }
}
