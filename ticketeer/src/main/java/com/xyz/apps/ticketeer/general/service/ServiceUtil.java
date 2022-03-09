/*
* Id: ServiceUtil.java 09-Mar-2022 6:57:43 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import org.springframework.http.ResponseEntity;

/**
 * The service util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class ServiceUtil {

    private ServiceUtil() {

    }

    /**
     * Indicates if has body response entity.
     *
     * @param <T> the generic type
     * @param responseEntity the response entity
     * @return true, if successful
     */
    public static <T> boolean hasBodyResponseEntity(final ResponseEntity<T> responseEntity) {
        return responseEntity != null && responseEntity.hasBody();
    }

    /**
     * Not has body response entity.
     *
     * @param <T> the generic type
     * @param responseEntity the response entity
     * @return true, if successful
     */
    public static <T> boolean notHasBodyResponseEntity(final ResponseEntity<T> responseEntity) {
        return !(hasBodyResponseEntity(responseEntity));
    }
}
