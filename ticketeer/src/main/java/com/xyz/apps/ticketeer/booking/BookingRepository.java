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
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Query("update Booking b set b.bookingStatus = BookingStatus.CANCELLED.ordinal() where b.bookingId = :id")
    public void cancelById(@Param("id") final Long id);

}
