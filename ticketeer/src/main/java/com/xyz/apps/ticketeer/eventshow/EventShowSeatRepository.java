/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The event show seat repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface EventShowSeatRepository extends JpaRepository<EventShowSeat, Long> {

    /**
     * Cancel by booking id.
     *
     * @param bookingId the booking id
     */
    @Modifying
    @Query("update EventShowSeat ess set ess.seatReservationStatus = SeatReservationStatus.AVAILABLE.ordinal(), ess.bookingId = null where ess.bookingId = :bookingId")
    public void cancelByBookingId(@Param("bookingId") final Long bookingId);

    /**
     * Are seats available.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @return true, if successful
     */
    @Query(value = "select case when count(ess.seat_reservation_status) = :seatsCount then true else false from event_show_seat ess where ess.id in :ids and ess.seat_reservation_status = 0",
           nativeQuery = true)
    public boolean areSeatsAvailable(@Param("ids") final Collection<Long> ids, @Param("seatCount") final int seatsCount);

    /**
     * Are seats reserved.
     *
     * @param ids the ids
     * @param bookingId the booking id
     * @param seatsCount the seats count
     * @return true, if successful
     */
    @Query(value = "select case when count(ess.seat_reservation_status) = :seatsCount then true else false from event_show_seat ess"
        + " where ess.id in :ids"
        + " and ess.seat_reservation_status = 1"
        + " and ess.booking_id = :bookingId ",
            nativeQuery = true)
     public boolean areSeatsReserved(@Param("ids") final Collection<Long> ids,
                                     @Param("bookingId") final Long bookingId,
                                     @Param("seatCount") final int seatsCount);

    /**
     * Reserve seats.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @return the int
     */
    @Modifying
    @Query(value = "update EventShowSeat ess set ess.seat_reservation_status = 1, ess.reservation_time = current_timestamp() where ess.id in :ids "
        + "and (select count(ess1.seat_reservation_status) from event_show_seat ess1 where ess1.id in :ids and ess1.seat_reservation_status = 0) = :seatsCount",
           nativeQuery = true)
    public int reserveSeats(@Param("ids") final Collection<Long> ids, @Param("seatCount") final int seatsCount);

    /**
     * Fill booking for reserved seats.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @param bookingId the booking id
     * @return the int
     */
    @Modifying
    @Query(value = "update EventShowSeat ess set ess.booking_id = :bookingId where ess.id in :ids "
        + "and (select count(ess1.seat_reservation_status) from event_show_seat ess1 where ess1.id in :ids and ess1.seat_reservation_status = 1) = :seatsCount",
           nativeQuery = true)
    public int fillBookingForReservedSeats(@Param("ids") final Collection<Long> ids,
            @Param("seatCount") final int seatsCount,
            @Param("bookingId") final Long bookingId);
}
