/*
* Id: RestServiceException.java 17-Mar-2022 12:05:38 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import org.springframework.http.HttpStatus;

/**
 * The rest service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public interface RestServiceException {

    HttpStatus httpStatus();
}
