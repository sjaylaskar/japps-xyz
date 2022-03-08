/*
* Id: PlatformConvenienceFeeRepository.java 05-Mar-2022 2:58:39 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * The platform convenience fee repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public interface PlatformConvenienceFeeRepository extends MongoRepository<PlatformConvenienceFee, ObjectId> {

}
