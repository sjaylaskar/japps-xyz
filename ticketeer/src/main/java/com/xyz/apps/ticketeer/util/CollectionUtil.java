/*
* Id: CollectionUtil.java 06-Mar-2022 2:23:48 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The collection util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class CollectionUtil {

    private CollectionUtil() {

    }

    private static final String COMMA = ",";

    /**
     * Stringify.
     *
     * @param <T> the generic type
     * @param collection the collection
     * @return the string
     */
    public static <T> String stringify(final Collection<T> collection) {
        return collection.stream().map(Object::toString).collect(Collectors.joining(COMMA));
    }
}
