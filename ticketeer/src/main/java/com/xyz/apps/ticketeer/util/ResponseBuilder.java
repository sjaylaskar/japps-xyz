/*
* Id: ResponseBuilder.java 29-Mar-2022 3:40:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The response builder.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class ResponseBuilder {

    /**
     * Instantiates a new response builder.
     */
    private ResponseBuilder() {

    }

    /**
     * Ok.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<?> ok(final Object body) {
        return ResponseEntity.ok(body);
    }

    /**
     * Accepted.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<?> accepted(final Object body) {
        return ResponseEntity.accepted().body(body);
    }

    /**
     * Created.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<?> created(final Object body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
