/*
 * Id: CountryService.java 14-Feb-2022 1:19:40 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * The country service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class CountryService {

    /** The country repository. */
    @Autowired
    private CountryRepository countryRepository;

    /**
     * Adds the country.
     *
     * @param country the country
     * @return the country
     */
    public Country add(final Country country) {

        return countryRepository.save(country);
    }

    /**
     * Adds all.
     *
     * @param countries the countries
     * @return the list of countries
     */
    public List<Country> addAll(final List<Country> countries) {
        return countryRepository.saveAll(countries);
    }

    /**
     * Updates the country.
     *
     * @param country the country
     * @return the country
     */
    public Country update(final Country country) {

        return countryRepository.save(country);
    }

    /**
     * Delete.
     *
     * @param country the country
     */
    public void delete(final Country country) {

        countryRepository.delete(country);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(final Long id) {

        countryRepository.deleteById(id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    public void deleteByCode(final String code) {

        countryRepository.deleteByCode(code);
    }

    /**
     * Delete by name.
     *
     * @param name the name
     */
    public void deleteByName(final String name) {

        countryRepository.deleteByName(name);
    }

    /**
     * Finds the country by id.
     *
     * @param id the id
     * @return the country
     */
    public Country findById(final Long id) {

        return countryRepository.findById(id).orElse(null);
    }

    /**
     * Finds the country by code.
     *
     * @param code the code
     * @return the country
     */
    public Country findByCode(final String code) {

        return countryRepository.findByCode(code);
    }

    /**
     * Finds the country by name.
     *
     * @param name the name
     * @return the country
     */
    public Country findByName(final String name) {

        return countryRepository.findByName(name);
    }

    /**
     * Finds all countries.
     *
     * @return the list
     */
    public List<Country> findAll() {

        return countryRepository.findAll();
    }

}
