/*
* Id: GeneralService.java 07-Mar-2022 11:50:46 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * The service beans fetcher.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class ServiceBeansFetcher {

    /** The environment. */
    @Autowired
    private Environment environment;

    /** The mongo template. */
    @Autowired
    private MongoTemplate mongoTemplate;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /** The web client builder. */
    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Environment.
     *
     * @return the environment
     */
    public Environment environment() {
        return environment;
    }

    /**
     * Mongo template.
     *
     * @return the mongo template
     */
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    /**
     * Rest template.
     *
     * @return the rest template
     */
    public RestTemplate restTemplate() {
        return restTemplate;
    }

    /**
     * Web client builder.
     *
     * @return the web client. builder
     */
    public WebClient.Builder webClientBuilder() {
        return webClientBuilder;
    }
}
