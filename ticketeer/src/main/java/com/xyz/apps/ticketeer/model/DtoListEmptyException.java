/*
* Id: DtoListEmptyException.java 05-Mar-2022 1:17:27 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.model;


/**
 * The dto list empty exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class DtoListEmptyException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5524458282392852630L;

    /**
     * Instantiates a new dto list empty exception.
     *
     * @param message the message
     */
    public DtoListEmptyException(final String message) {
        super(message);
    }
}
