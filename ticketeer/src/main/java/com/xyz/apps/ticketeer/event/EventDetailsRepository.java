/*
* Id: EventDetailsRepository.java 03-Mar-2022 4:11:45 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * The event details repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public interface EventDetailsRepository extends MongoRepository<EventDetails, Long> {

}
