/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
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

    /**
     * Finds the by username and id.
     *
     * @param username the username
     * @param id the id
     * @return the booking
     */
    @Query("select bk from Booking bk where"
        + " bk.username = :username"
        + " and bk.id = :id"
        + " and bk.bookingStatus in (com.xyz.apps.ticketeer.booking.model.BookingStatus.CONFIRMED, com.xyz.apps.ticketeer.booking.model.BookingStatus.CANCELLED)")
    public Booking findByUsernameAndId(@Param("username") final String username, @Param("id") final Long id);

    /**
     * Finds the confirmed booking by username and id.
     *
     * @param username the username
     * @param id the id
     * @return the booking
     */
    @Query("select bk from Booking bk where"
            + " bk.username = :username"
            + " and bk.id = :id"
            + " and bk.bookingStatus = com.xyz.apps.ticketeer.booking.model.BookingStatus.CONFIRMED")
    public Booking findConfirmedBookingByUsernameAndId(@Param("username") final String username, @Param("id") final Long id);

    /**
     * Finds the by username.
     *
     * @param username the username
     * @return the list
     */
    @Query("select bk from Booking bk where"
        + " bk.username = :username"
        + " and bk.bookingStatus in (com.xyz.apps.ticketeer.booking.model.BookingStatus.CONFIRMED, com.xyz.apps.ticketeer.booking.model.BookingStatus.CANCELLED)")
    public List<Booking> findByUsername(@Param("username") final String username);

    /**
     * Finds the first by id and booking reservation id and username and event show id order by reservation time asc.
     *
     * @return the optional
     */
    public Optional<Booking> findFirstByIdAndBookingReservationIdAndEventShowIdAndUsernameOrderByReservationTimeAsc(
            @Param("id") final Long id,
            @Param("bookingReservationId") final UUID bookingReservationId,
            @Param("eventShowId") final Long eventShowId,
            @Param("username") final String username);
}
