/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

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

    @Query(value = "select * from event_show_seat ess where ess.event_show_id = :eventShowId",
           nativeQuery = true)
    public List<EventShowSeat> findByEventShowId(@Param("eventShowId") final Long eventShowId);

    /**
     * Cancel by booking id.
     *
     * @param bookingId the booking id
     */
    @Transactional
    @Modifying
    @Query(value = "update event_show_seat ess set ess.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.SeatReservationStatus.AVAILABLE.name(), ess.booking_id = null where ess.booking_id = :bookingId",
           nativeQuery = true)
    public void cancelByBookingId(@Param("bookingId") final Long bookingId);

    /** The are seats available query. */
    static final String ARE_SEATS_AVAILABLE_QUERY = "select case when count(ess.seat_reservation_status) = :seatsCount then true else false from event_show_seat ess where ess.id in :ids and ess.seat_reservation_status = 'AVAILABLE'";
    /**
     * Are seats available.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @return true, if successful
     */
    @Query(value = ARE_SEATS_AVAILABLE_QUERY,
           nativeQuery = true)
    public boolean areSeatsAvailable(@Param("ids") final Collection<Long> ids, @Param("seatsCount") final int seatsCount);

    /** The are seats reserved query. */
    static final String ARE_SEATS_RESERVED_QUERY = "select case when count(ess.seat_reservation_status) = :seatsCount then true else false from event_show_seat ess"
            + " where ess.id in :ids"
            + " and ess.seat_reservation_status = 'RESERVED'"
            + " and ess.booking_id = :bookingId ";
    /**
     * Are seats reserved.
     *
     * @param ids the ids
     * @param bookingId the booking id
     * @param seatsCount the seats count
     * @return true, if successful
     */
    @Query(value = ARE_SEATS_RESERVED_QUERY,
            nativeQuery = true)
     public boolean areSeatsReserved(@Param("ids") final Collection<Long> ids,
                                     @Param("bookingId") final Long bookingId,
                                     @Param("seatsCount") final int seatsCount);

    /** The reserve seats query. */
    static final String RESERVE_SEATS_QUERY = "update event_show_seat ess set ess.seat_reservation_status = 'RESERVED', ess.reservation_time = current_timestamp() where ess.id in :ids "
            + "and (select count(ess1.seat_reservation_status) from event_show_seat ess1 where ess1.id in :ids and ess1.seat_reservation_status = 'AVAILABLE') = :seatsCount";
    /**
     * Reserve seats.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @return the int
     */
    @Transactional
    @Modifying
    @Query(value = RESERVE_SEATS_QUERY,
           nativeQuery = true)
    public int reserveSeats(@Param("ids") final Collection<Long> ids, @Param("seatsCount") final int seatsCount);

    /** The unreserve seats query. */
    static final String UNRESERVE_SEATS_QUERY = "update event_show_seat ess set ess.seat_reservation_status = 'AVAILABLE', ess.reservation_time = null where ess.id in :ids "
            + "and (select count(ess1.seat_reservation_status) from event_show_seat ess1 where ess1.id in :ids and ess1.seat_reservation_status = 'RESERVED') = :seatsCount";
    /**
     * Unreserve seats.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @return the int
     */
    @Transactional
    @Modifying
    @Query(value = UNRESERVE_SEATS_QUERY,
           nativeQuery = true)
    public int unreserveSeats(@Param("ids") final Collection<Long> ids, @Param("seatsCount") final int seatsCount);

    /** The book seats query. */
    static final String BOOK_SEATS_QUERY = "update event_show_seat ess set ess.seat_reservation_status = 'BOOKED' where ess.booking_id = :bookingId and ess.id in :ids "
            + "and (select count(ess1.seat_reservation_status) from event_show_seat ess1 where ess1.booking_id = :bookingId and ess1.id in :ids and ess1.seat_reservation_status = 'RESERVED') = :seatsCount";
    /**
     * Book seats.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @param bookingId the booking id
     * @return the int
     */
    @Transactional
    @Modifying
    @Query(value = BOOK_SEATS_QUERY,
           nativeQuery = true)
    public int bookSeats(@Param("ids") final Collection<Long> ids,
            @Param("seatsCount") final int seatsCount,
            @Param("bookingId") final Long bookingId);

    /** The fill booking for reserved seats query. */
    static final String FILL_BOOKING_FOR_RESERVED_SEATS_QUERY = "update event_show_seat ess set ess.booking_id = :bookingId where ess.id in :ids "
            + "and (select count(ess1.seat_reservation_status) from event_show_seat ess1 where ess1.booking_id = :bookingId and ess1.id in :ids and ess1.seat_reservation_status = 'RESERVED') = :seatsCount";
    /**
     * Fill booking for reserved seats.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @param bookingId the booking id
     * @return the int
     */
    @Transactional
    @Modifying
    @Query(value = FILL_BOOKING_FOR_RESERVED_SEATS_QUERY,
           nativeQuery = true)
    public int fillBookingForReservedSeats(@Param("ids") final Collection<Long> ids,
            @Param("seatsCount") final int seatsCount,
            @Param("bookingId") final Long bookingId);

    /**
     * Finds the total amount.
     *
     * @param ids the ids
     * @return the double
     */
    @Query("select sum(ess.amount) from EventShowSeat ess where ess.id in :ids")
    public double findTotalAmount(@Param("ids") final Collection<Long> ids);
}
