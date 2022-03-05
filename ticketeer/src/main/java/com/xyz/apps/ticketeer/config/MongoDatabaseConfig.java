/*
 * Id: MongoDbConfig.java 03-Mar-2022 5:27:43 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.xyz.apps.ticketeer.util.Environment;


/**
 * The mongo database config.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Configuration
public class MongoDatabaseConfig {

    /**
     * Mongo database factory.
     *
     * @return the mongo database factory
     */
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(Environment.property("spring.data.mongodb.uri"));
    }

    /**
     * Mongo template.
     *
     * @return the mongo template
     */
    @Bean
    public MongoTemplate mongoTemplate() {

        return new MongoTemplate(mongoDatabaseFactory());
    }

}
