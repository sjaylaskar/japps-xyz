/*
* Id: PlatformConvenienceFeeNotFoundException.java 05-Mar-2022 4:22:52 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.service;

import org.springframework.context.MessageSource;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.pricing.conveniencefee.resources.Messages;
import com.xyz.apps.ticketeer.util.MessageUtil;

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
    public PlatformConvenienceFeeNotFoundException(final MessageSource messageSource) {
        super(MessageUtil.defaultLocaleMessage(messageSource, Messages.MESSAGE_ERROR_NOT_FOUND_PLATFORM_CONVENIENCE_FEE, null));
    }

    /**
     * Instantiates a new platform convenience fee not found exception.
     *
     * @param id the id
     */
    public PlatformConvenienceFeeNotFoundException(final MessageSource messageSource, final String id) {
        super(MessageUtil.defaultLocaleMessage(messageSource, Messages.MESSAGE_ERROR_NOT_FOUND_PLATFORM_CONVENIENCE_FEE_ID, id));
    }
}
