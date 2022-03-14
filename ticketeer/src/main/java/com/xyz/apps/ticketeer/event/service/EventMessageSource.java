/*
 * Id: MessageResourceFinder.java 13-Mar-2022 1:42:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.event.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.xyz.apps.ticketeer.general.service.ServiceMessageSource;


/**
 * The event message source.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class EventMessageSource extends ServiceMessageSource {

    /**
     * Adds the message sources.
     */
    @PostConstruct
    private void add() {
        super.add("classpath:messages/event/Messages",
                  "classpath:messages/event/ExternalApiUrls");
    }
}
