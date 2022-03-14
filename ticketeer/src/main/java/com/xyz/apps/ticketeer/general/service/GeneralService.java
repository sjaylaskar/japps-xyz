/*
* Id: GeneralService.java 07-Mar-2022 12:09:11 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * The general service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class GeneralService {

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

    /** The message source. */
    @Autowired
    private MessageSource messageSource;

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

    /**
     * Message source.
     *
     * @return the message source
     */
    public MessageSource messageSource() {
        return messageSource;
    }
}
