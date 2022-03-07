/*
* Id: GeneralService.java 07-Mar-2022 12:09:11 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.apps.ticketeer.util.ServiceBeansFetcher;

/**
 * The general service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class GeneralService {

    /** The service beans fetcher. */
    @Autowired
    private ServiceBeansFetcher serviceBeansFetcher;

    /**
     * Service beans fetcher.
     *
     * @return the service beans fetcher
     */
    protected ServiceBeansFetcher serviceBeansFetcher() {
        return serviceBeansFetcher;
    }
}
