/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * The booking repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Finds the successful payment by booking id.
     *
     * @param bookingId the booking id
     * @return the payment
     */
    @Query("select pt from Payment pt where pt.booking = :booking and pt.paymentStatus = com.xyz.apps.ticketeer.booking.PaymentStatus.SUCCESS")
    public Payment findSuccessfulPaymentByBooking(final Booking booking);

}
