/*
* Id: DiscountRepository.java 05-Mar-2022 12:32:55 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * The booking details repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface BookingDetailsRepository extends MongoRepository<BookingDetails, ObjectId> {

    /**
     * Delete by booking id.
     *
     * @param bookingId the booking id
     */
    @Transactional
    @Query(delete = true)
    public Long deleteByBookingId(final Long bookingId);
}
