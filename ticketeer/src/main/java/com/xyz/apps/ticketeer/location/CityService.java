/*
 * Id: CityService.java 14-Feb-2022 2:16:34 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


/**
 * The city service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Log4j2
public class CityService {

    /** The city repository. */
    @Autowired
    private CityRepository cityRepository;

    /** The country repository. */
    @Autowired
    private CountryRepository countryRepository;

    /**
     * Adds the city.
     *
     * @param city the city
     * @return the city
     */
    public City add(final City city) {

        return cityRepository.save(city);
    }

    /**
     * Adds all.
     *
     * @param cities the cities
     * @return the list of cities.
     */
    public List<City> addAll(final List<City> cities) {
        return cityRepository.saveAll(cities);
    }

    /**
     * Updates the city.
     *
     * @param city the city
     * @return the city
     */
    public City update(final City city) {

        return cityRepository.save(city);
    }

    /**
     * Delete.
     *
     * @param city the city
     */
    public void delete(final City city) {

        cityRepository.delete(city);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(final Long id) {

        cityRepository.deleteById(id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    public void deleteByCode(final String code) {

        cityRepository.deleteByCode(code);
    }

    /**
     * Finds the city by id.
     *
     * @param id the id
     * @return the city
     */
    public City findById(final Long id) {

        return cityRepository.findById(id).orElse(null);
    }

    /**
     * Finds the city by code.
     *
     * @param code the code
     * @return the city
     */
    public City findByCode(final String code) {

        return cityRepository.findByCode(code);
    }

    /**
     * Finds the cities by name.
     *
     * @param name the name
     * @return the cities
     */
    public List<City> findByName(final String name) {

        return cityRepository.findByName(name);
    }

    /**
     * Finds the cities by country.
     *
     * @param countryId the country id
     * @return the list of cities
     * @throws CountryNotFoundException in case the country is not found
     */
    public List<City> findByCountry(final Long countryId) throws CountryNotFoundException {
        final Country country = countryRepository.findById(countryId).orElse(null);
        if (country == null) {
            log.error("Country not found for id: " + countryId);
            throw new CountryNotFoundException(countryId);
        }
        return cityRepository.findByCountry(country);
    }

    /**
     * Finds the by country code.
     *
     * @param countryCode the country code
     * @return the list of cities
     */
    public List<City> findByCountryCode(final String countryCode) {
        final Country country = countryRepository.findByCode(countryCode);
        if (country == null) {
            log.error("Country not found for code: " + countryCode);
            throw new CountryNotFoundException(countryCode);
        }
        return cityRepository.findByCountryCode(countryCode);
    }

    /**
     * Finds all cities.
     *
     * @return the list
     */
    public List<City> findAll() {

        return cityRepository.findAll();
    }
}
