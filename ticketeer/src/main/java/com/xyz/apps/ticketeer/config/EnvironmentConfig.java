/*
* Id: Config.java 07-Mar-2022 11:37:37 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * The environment config.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Configuration
public class EnvironmentConfig {

    /** The environment. */
    @Autowired
    protected Environment environment;
}
