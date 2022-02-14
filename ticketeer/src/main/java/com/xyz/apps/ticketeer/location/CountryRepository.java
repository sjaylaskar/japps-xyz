/*
* Id: CountryRepository.java 14-Feb-2022 1:17:04 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The country repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    /**
     * Finds the Country by code.
     *
     * @param code the code
     * @return the country
     */
    Country findByCode(final String code);

    /**
     * Finds the Country by name.
     *
     * @param name the name
     * @return the country
     */
    Country findByName(final String name);

}
