/*
* Id: AuditoriumSeatRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
     * @param auditoriumId the auditorium id
     * @param seatRows the seat rows
     * @return the list of {@link AuditoriumSeat}
     */
    @Query(value = "select * from auditorium_seat aust where aust.auditorium_id = :auditoriumId and aust.seat_row in :seatRows",
           nativeQuery = true)
    public List<AuditoriumSeat> findBySeatRowIn(@Param("auditoriumId") final Long auditoriumId, @Param("seatRows") final Collection<Character> seatRows);

    /**
     * Finds the by auditorium id.
     *
     * @param auditorium the auditorium
     * @return the list of {@link AuditoriumSeat}
     */
    public List<AuditoriumSeat> findByAuditorium(final Auditorium auditorium);

    /**
     * Finds the by auditorium and row name.
     *
     * @param auditorium the auditorium
     * @param rowName the row name
     * @return the list
     */
    public Optional<AuditoriumSeat> findTopByAuditoriumAndRowNameOrderBySeatNumberDesc(final Auditorium auditorium, final String rowName);

    /**
     * Delete by auditorium.
     *
     * @param auditorium the auditorium
     * @return the long
     */
    @Transactional
    @Modifying
    public Long deleteByAuditorium(final Auditorium auditorium);

    /**
     * Delete by auditorium and row name.
     *
     * @param auditorium the auditorium
     * @param rowName the row name
     * @return the long
     */
    @Transactional
    @Modifying
    public Long deleteByAuditoriumAndRowName(final Auditorium auditorium, final String rowName);

    /**
     * Delete by auditorium and row name and seat number.
     *
     * @param auditorium the auditorium
     * @param rowName the row name
     * @param seatNumber the seat number
     * @return the long
     */
    @Transactional
    @Modifying
    public Long deleteByAuditoriumAndRowNameAndSeatNumber(final Auditorium auditorium, final String rowName, final Integer seatNumber);
}
