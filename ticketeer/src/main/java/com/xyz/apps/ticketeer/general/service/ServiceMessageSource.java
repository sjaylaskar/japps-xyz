/*
 * Id: ServiceMessageSource.java 13-Mar-2022 1:42:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;


/**
 * The service message source.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class ServiceMessageSource {

    /** The message source. */
    @Autowired
    private MessageSource messageSource;

    /**
     * Adds the message sources.
     */
    protected void add(final String ...baseNames) {
        if (messageSource instanceof ReloadableResourceBundleMessageSource) {
            ((ReloadableResourceBundleMessageSource) messageSource).addBasenames(baseNames);
        }
    }

    /**
     * Gets the message source.
     *
     * @return the message source
     */
    public MessageSource get() {
        return messageSource;
    }

}
