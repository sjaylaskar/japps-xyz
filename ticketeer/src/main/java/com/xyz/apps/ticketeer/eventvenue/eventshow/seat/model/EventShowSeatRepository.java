/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model;

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

    /** The cancel booked seats query. */
    static final String CANCEL_BOOKED_SEATS_QUERY = "update EventShowSeat ess"
        + " set ess.seatReservationStatus = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.AVAILABLE.name(),"
        + " ess.bookingId = null,"
        + " ess.bookingTime = null,"
        + " ess.reservationTime = null"
        + " where ess.bookingId = :bookingId";
    /**
     * Cancel by booking id.
     *
     * @param bookingId the booking id
     */
    @Transactional
    @Modifying
    @Query(CANCEL_BOOKED_SEATS_QUERY)
    public void cancelByBookingId(@Param("bookingId") final Long bookingId);

    /** The are seats available query. */
    static final String ARE_SEATS_AVAILABLE_QUERY = "select case when count(ess.seatReservationStatus) = :seatsCount then true else false end from EventShowSeat ess"
        + " where ess.id in :ids and ess.seatReservationStatus = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.AVAILABLE";
    /**
     * Are seats available.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @return true, if successful
     */
    @Query(ARE_SEATS_AVAILABLE_QUERY)
    public boolean areSeatsAvailable(@Param("ids") final Collection<Long> ids, @Param("seatsCount") final long seatsCount);

    /** The are seats reserved query. */
    static final String ARE_SEATS_RESERVED_QUERY = "select case when count(ess.seatReservationStatus) = :seatsCount then true else false end from EventShowSeat ess"
            + " where ess.id in :ids"
            + " and ess.seatReservationStatus = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.RESERVED"
            + " and ess.bookingId = :bookingId ";
    /**
     * Are seats reserved.
     *
     * @param ids the ids
     * @param bookingId the booking id
     * @param seatsCount the seats count
     * @return true, if successful
     */
    @Query(ARE_SEATS_RESERVED_QUERY)
     public boolean areSeatsReserved(@Param("ids") final Collection<Long> ids,
                                     @Param("bookingId") final Long bookingId,
                                     @Param("seatsCount") final long seatsCount);

    /** The reserve seats query. */
    static final String RESERVE_SEATS_QUERY =  "update event_show_seat ess1"
        + "  inner join ("
        + "    select count(ess.seat_reservation_status) count_avail_status"
        + "    from event_show_seat ess"
        + "    where ess.id in :ids"
        + "    and ess.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.AVAILABLE.name()"
        + "  ) ess2"
        + " set ess1.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.RESERVED.name(),"
        + " ess1.reservation_time = java.util.LocalDateTime.now()"
        + " where ess1.id in :ids"
        + " and ess1.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.AVAILABLE.name()"
        + " and ess2.count_avail_status = :seatsCount";

    /**
     * Reserve seats.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @return the int
     */
    @Transactional
    @Modifying
    @Query(value = RESERVE_SEATS_QUERY, nativeQuery = true)
    public int reserveSeats(@Param("ids") final Collection<Long> ids, @Param("seatsCount") final long seatsCount);

    /** The unreserve seats query. */
    static final String UNRESERVE_SEATS_QUERY = "update EventShowSeat ess"
        + " set ess.seatReservationStatus = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.AVAILABLE,"
        + " ess.reservationTime = null"
        + " where ess.id in :ids";

    /**
     * Unreserve seats.
     *
     * @param ids the ids
     * @param seatsCount the seats count
     * @return the int
     */
    @Transactional
    @Modifying
    @Query(value = UNRESERVE_SEATS_QUERY)
    public int unreserveSeats(@Param("ids") final Collection<Long> ids);

    /** The book seats query. */
    static final String BOOK_SEATS_QUERY =  "update event_show_seat ess1"
            + "  inner join ("
            + "    select count(ess.seat_reservation_status) count_reserved_status"
            + "    from event_show_seat ess"
            + "    where ess.booking_id = :bookingId"
            + "    and ess.id in :ids"
            + "    and ess.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.RESERVED.name()"
            + "  ) ess2"
            + " set ess1.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.BOOKED.name(),"
            + " ess1.booking_time = java.util.LocalDateTime.now()"
            + " where ess1.booking_id = :bookingId"
            + " and ess1.id in :ids"
            + " and ess1.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.RESERVED.name()"
            + " and ess2.count_reserved_status = :seatsCount";

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
    @Query(value = BOOK_SEATS_QUERY, nativeQuery = true)
    public int bookSeats(@Param("ids") final Collection<Long> ids,
            @Param("seatsCount") final int seatsCount,
            @Param("bookingId") final Long bookingId);

    /** The fill booking for reserved seats query. */
    static final String FILL_BOOKING_FOR_RESERVED_SEATS_QUERY =  "update event_show_seat ess1"
            + "  inner join ("
            + "    select count(ess.seat_reservation_status) count_reserved_status"
            + "    from event_show_seat ess"
            + "    where ess.id in :ids"
            + "    and ess.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.RESERVED.name()"
            + "  ) ess2"
            + " set ess1.booking_id = :bookingId"
            + " where ess1.id in :ids"
            + " and ess1.seat_reservation_status = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.RESERVED.name()"
            + " and ess2.count_reserved_status = :seatsCount";

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
    @Query(value = FILL_BOOKING_FOR_RESERVED_SEATS_QUERY, nativeQuery = true)
    public int fillBookingForReservedSeats(@Param("ids") final Collection<Long> ids,
            @Param("seatsCount") final long seatsCount,
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
