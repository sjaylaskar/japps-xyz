/*
 * Id: EventShowMessageSource.java 13-Mar-2022 1:42:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.xyz.apps.ticketeer.general.service.ServiceMessageSource;


/**
 * The event show message source.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class EventShowMessageSource extends ServiceMessageSource {

    /**
     * Adds the message sources.
     */
    @PostConstruct
    private void add() {
        super.add("classpath:module/eventvenue/eventshow/Messages");
    }
}
