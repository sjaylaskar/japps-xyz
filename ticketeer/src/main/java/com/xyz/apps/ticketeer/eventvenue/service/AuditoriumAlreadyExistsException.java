/*
* Id: AuditoriumAlreadyExistsException.java 15-Mar-2022 3:24:44 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

import com.xyz.apps.ticketeer.eventvenue.resources.Messages;
import com.xyz.apps.ticketeer.general.service.AlreadyExistsException;

/**
 * The auditorium already exists exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class AuditoriumAlreadyExistsException extends AlreadyExistsException {

    /** The serial version UID. */
    private static final long serialVersionUID = 5465605448320833668L;

    public AuditoriumAlreadyExistsException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
