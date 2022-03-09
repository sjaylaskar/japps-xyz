/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The auditorium seat repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface AuditoriumSeatRepository extends JpaRepository<AuditoriumSeat, Long> {

    /**
     * Finds the by seat row in.
     *
     * @param seatRows the seat rows
     * @return the list of {@link AuditoriumSeat}
     */
    @Query(value = "select * from auditorium_seat aust where aust.auditorium_id = :auditoriumId and aust.seat_row in :seatRows",
           nativeQuery = true)
    public List<AuditoriumSeat> findBySeatRowIn(@Param("auditoriumId") final Long auditoriumId, @Param("seatRows") final Collection<Character> seatRows);
}
