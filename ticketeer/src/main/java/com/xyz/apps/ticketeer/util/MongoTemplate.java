/*
* Id: MongoTemplate.java 05-Mar-2022 1:43:01 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The mongo template.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component(value = "appMongoTemplate")
public final class MongoTemplate {

    /**
     * Instantiates a new mongo template.
     */
    private MongoTemplate() {

    }

    /** The instance. */
    private static final MongoTemplate instance = new MongoTemplate();

    /** The mongo template. */
    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    /**
     * Instance.
     *
     * @return the mongo template
     */
    private static MongoTemplate instance() {
        return instance;
    }

    /**
     * Gets the spring data mongo template.
     *
     * @return the org.springframework.data.mongodb.core. mongo template
     */
    public static org.springframework.data.mongodb.core.MongoTemplate get() {
        return instance().mongoTemplate;
    }
}
