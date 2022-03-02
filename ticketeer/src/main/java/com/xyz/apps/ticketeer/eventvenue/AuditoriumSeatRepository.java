/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The auditorium seat repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface AuditoriumSeatRepository extends JpaRepository<AuditoriumSeat, Long> {

}