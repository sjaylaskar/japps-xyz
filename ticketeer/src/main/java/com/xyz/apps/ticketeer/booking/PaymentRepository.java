/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The booking repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    @Query("update Payment p set p.paymentStatus = PaymentStatus.REFUNDED.ordinal() where p.bookingId = :id and p.paymentStatus = PaymentStatus.SUCCESS.ordinal()")
    public void refundPaymentById(@Param("id") final Long id);

}
