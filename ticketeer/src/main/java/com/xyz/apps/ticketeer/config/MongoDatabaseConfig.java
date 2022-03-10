/*
 * Id: MongoDbConfig.java 03-Mar-2022 5:27:43 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


/**
 * The mongo database config.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Configuration
public class MongoDatabaseConfig extends AbstractMongoClientConfiguration {

    /** The environment. */
    @Autowired
    private Environment environment;

    @Override
    public MongoClient mongoClient() {

        return MongoClients.create(environment.getProperty("spring.data.mongodb.uri"));
    }

    @Override
    protected String getDatabaseName() {

        return environment.getProperty("xyz.ticketeer.mongodb.dbname");
    }

    @Override
    protected boolean autoIndexCreation() {

        return true;
    }

}
