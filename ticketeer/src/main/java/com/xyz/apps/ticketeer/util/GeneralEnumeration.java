/*
* Id: GeneralEnumeration.java 07-Mar-2022 2:47:30 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

/**
 * The general enumeration.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public interface GeneralEnumeration<T extends Enum<T>> {

    /**
     * Of.
     *
     * @param name the name
     * @return the general enumeration
     */
    public default T of(final String name) {

        return valueOf(name);
    }

    public T valueOf(final String name);
}
