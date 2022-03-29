/*
* Id: RestResponse.java 29-Mar-2022 3:40:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The rest response.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class RestResponse {

    /**
     * Instantiates a new rest response.
     */
    private RestResponse() {

    }

    /**
     * Ok.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<Object> ok(final Object body) {
        return ResponseEntity.ok(body);
    }

    /**
     * Accepted.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<Object> accepted(final Object body) {
        return ResponseEntity.accepted().body(body);
    }

    /**
     * Created.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<Object> created(final Object body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    /**
     * Bad request.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<Object> badRequest(final Object body) {
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Conflict.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<Object> conflict(final Object body) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Internal server error.
     *
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<Object> internalServerError(final Object body) {
        return ResponseEntity.internalServerError().body(body);
    }

    /**
     * Of.
     *
     * @param httpStatus the http status
     * @param body the body
     * @return the response entity
     */
    public static ResponseEntity<Object> of(final HttpStatus httpStatus, final Object body) {
        return ResponseEntity.status(httpStatus).body(body);
    }
}
