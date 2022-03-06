/*
* Id: Environment.java 05-Mar-2022 1:23:11 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The environment.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component(value = "appEnvironment")
public final class Environment {

    /**
     * Instantiates a new environment.
     */
    private Environment() {

    }

    /** The environment. */
    @Autowired
    private org.springframework.core.env.Environment environment;

    /** The instance. */
    private static final Environment instance = new Environment();

    /**
     * Instance.
     *
     * @return the environment
     */
    private static Environment instance() {
        return instance;
    }

    /**
     * Gets the.
     *
     * @return the org.springframework.core.env. environment
     */
    public static org.springframework.core.env.Environment get() {
        return instance().environment;
    }

    /**
     * Property.
     *
     * @param key the key
     * @return the string
     */
    public static String property(final String key) {
        return get().getProperty(key);
    }
}
