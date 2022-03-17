/*
* Id: EventShowSeatRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShow;


/**
 * The event show seat repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface EventShowSeatRepository extends JpaRepository<EventShowSeat, Long> {

    /*
     * Basic EventShowSeat operations:
     */

    /**
     * Finds the by event show.
     *
     * @param eventShow the event show
     * @return the list
     */
    public List<EventShowSeat> findByEventShow(@Param("eventShow") final EventShow eventShow);

    /**
     * Finds the by event show and booking reservation id.
     *
     * @param eventShow the event show
     * @param bookingReservationId the booking reservation id
     * @return the list
     */
    public List<EventShowSeat> findByEventShowAndBookingReservationId(
            @Param("eventShow") final EventShow eventShow,
            @Param("bookingReservationId") final UUID bookingReservationId);

    /**
     * Finds the by event show and row name.
     *
     * @param eventShow the event show
     * @param rowName the row name
     * @return the list
     */
    public List<EventShowSeat> findByEventShowAndRowName(@Param("eventShow") final EventShow eventShow, @Param("rowName") final String rowName);

    /**
     * Delete by event show and row name.
     *
     * @param eventShow the event show
     * @param rowName the row name
     * @return the long
     */
    @Transactional
    @Modifying
    public Long deleteByEventShowAndRowName(@Param("eventShow") final EventShow eventShow, @Param("rowName") final String rowName);

    /**
     * Delete by event show and seat number.
     *
     * @param eventShow the event show
     * @param seatNumber the seat number
     * @return the long
     */
    @Transactional
    @Modifying
    public Long deleteByEventShowAndSeatNumber(@Param("eventShow") final EventShow eventShow, @Param("seatNumber") final String seatNumber);

    /**
     * Finds the by event show and seat numbers.
     *
     * @param eventShow the event show
     * @param seatNumbers the seat numbers
     * @return the list
     */
    @Query("select ess from EventShowSeat ess where ess.eventShow = :eventShow and ess.seatNumber in :seatNumbers")
    public List<EventShowSeat> findByEventShowAndSeatNumbers(@Param("eventShow") final EventShow eventShow, @Param("seatNumbers") final Collection<String> seatNumbers);

    /*
     * EventShowSeatReservation operations:
     */
    /** The reserve seats query. */
    static final String RESERVE_SEATS_QUERY =  "update event_show_seat ess1"
        + "  inner join ("
        + "    select count(ess.seat_reservation_status) count_avail_status"
        + "    from event_show_seat ess"
        + "    where "
        + "    ess.event_show_id = :eventShowId"
        + "    and ess.seat_number in :seatNumbers"
        + "    and ess.seat_reservation_status = 'AVAILABLE'"
        + "  ) ess2"
        + " set "
        + " ess1.seat_reservation_status = 'RESERVED',"
        + " ess1.booking_reservation_id = :bookingReservationId,"
        + " ess1.reservation_time = current_timestamp()"
        + " where "
        + " ess1.event_show_id = :eventShowId"
        + " and ess1.seat_number in :seatNumbers"
        + " and ess1.seat_reservation_status = 'AVAILABLE'"
        + " and ess2.count_avail_status = :seatsCount";

    /**
     * Reserve seats.
     *
     * @param eventShowId the event show id
     * @param seatNumbers the seat numbers
     * @param bookingReservationId the booking reservation id
     * @param seatsCount the seats count
     * @return the count of reserved seats.
     */
    @Transactional
    @Modifying
    @Query(value = RESERVE_SEATS_QUERY, nativeQuery = true)
    public Long reserve(
            @Param("eventShowId") final Long eventShowId,
            @Param("seatNumbers") final Collection<String> seatNumbers,
            @Param("bookingReservationId") final String bookingReservationId,
            @Param("seatsCount") final long seatsCount);

    /** The book seats query. */
    static final String BOOK_SEATS_QUERY =  "update event_show_seat ess1"
            + "  inner join ("
            + "    select count(ess.seat_reservation_status) count_reserved_status"
            + "    from event_show_seat ess"
            + "    where "
            + "    ess.booking_reservation_id = :bookingReservationId"
            + "    and ess.event_show_id = :event_show_id"
            + "    and ess.seat_number in :seatNumbers"
            + "    and ess.seat_reservation_status = 'RESERVED'"
            + "  ) ess2"
            + " set "
            + " ess1.seat_reservation_status = 'BOOKED',"
            + " ess1.booking_time = current_timestamp()"
            + " where"
            + " ess1.booking_reservation_id = :bookingReservationId"
            + " and ess1.event_show_id = :eventShowId"
            + " and ess1.seat_number in :seatNumbers"
            + " and ess1.seat_reservation_status = 'RESERVED'"
            + " and ess2.count_reserved_status = :seatsCount";

    /**
     * Book seats.
     *
     * @param eventShowId the event show id
     * @param seatNumbers the seat numbers
     * @param bookingReservationId the booking reservation id
     * @param seatsCount the seats count
     * @return the count of booked seats.
     */
    @Transactional
    @Modifying
    @Query(value = BOOK_SEATS_QUERY, nativeQuery = true)
    public Long book(
            @Param("eventShowId") final Long eventShowId,
            @Param("seatNumbers") final Collection<String> seatNumbers,
            @Param("bookingReservationId") final String bookingReservationId,
            @Param("seatsCount") final long seatsCount);

    /** The cancel booked seats query. */
    static final String CANCEL_BOOKED_SEATS_QUERY = "update EventShowSeat ess"
        + " set "
        + " ess.seatReservationStatus = com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.SeatReservationStatus.AVAILABLE,"
        + " ess.bookingReservationId = null,"
        + " ess.bookingTime = null,"
        + " ess.reservationTime = null"
        + " where"
        + " ess.bookingReservationId = :bookingReservationId"
        + " and ess.eventShow = :eventShow"
        + " and ess.seatNumber in :seatNumbers";

    /**
     * Cancel reserved/booked seats.
     *
     * @param eventShow the event show
     * @param seatNumbers the seat numbers
     * @param bookingReservationId the booking reservation id
     * @return the count of cancelled seats.
     */
    @Transactional
    @Modifying
    @Query(CANCEL_BOOKED_SEATS_QUERY)
    public Long cancel(
            @Param("eventShow") final EventShow eventShow,
            @Param("seatNumbers") final Collection<String> seatNumbers,
            @Param("bookingReservationId") final UUID bookingReservationId);

}
