/*
 * Id: CityService.java 14-Feb-2022 2:16:34 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.extern.log4j.Log4j2;


/**
 * The city service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Log4j2
@Validated
public class CityService {

    /** The city repository. */
    @Autowired
    private CityRepository cityRepository;

    /** The country service. */
    @Autowired
    private CountryService countryService;

    /** The city model mapper. */
    @Autowired
    private CityModelMapper cityModelMapper;

    /**
     * Adds the city.
     *
     * @param city the city
     * @return the city
     */
    public CityDto add(@NotNull(message = "The city cannot be null.") final CityDto cityDto) {
        return cityModelMapper.toDto(cityRepository.save(cityModelMapper.toEntity(cityDto)));
    }

    /**
     * Adds all.
     *
     * @param cities the cities
     * @return the list of cities.
     */
    public CityDtoList addAll(@NotNull(message = "The city list cannot be null.") final CityDtoList cityDtoList) {
        return CityDtoList.of(cityModelMapper.toDtos(cityRepository.saveAll(cityModelMapper.toEntities(cityDtoList.dtos()))));
    }

    /**
     * Updates the city.
     *
     * @param city the city
     * @return the city
     */
    public CityDto update(@NotNull(message = "The city cannot be null") final CityDto cityDto) {
        if (cityRepository.existsById(cityDto.getId())) {
            return cityModelMapper.toDto(cityRepository.save(cityModelMapper.toEntity(cityDto)));
        }
        throw new CityNotFoundException(cityDto);
    }

    /**
     * Delete.
     *
     * @param city the city
     */
    public void delete(@NotNull(message = "City cannot be null.") final CityDto cityDto) {
        if (cityRepository.existsById(cityDto.getId())) {
            cityRepository.delete(cityModelMapper.toEntity(cityDto));
        }

        throw new CityNotFoundException(cityDto);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(final Long id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
        }
        throw new CityNotFoundException(id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    public void deleteByCode(final String code) {
        final CityDto cityDto = findByCode(code);
        if (cityDto != null) {
            cityRepository.deleteByCode(code);
        }
        throw new CityNotFoundException(code);
    }

    /**
     * Finds the city by id.
     *
     * @param id the id
     * @return the city
     */
    public CityDto findById(final Long id) {
        return cityModelMapper.toDto(cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(id)));
    }

    /**
     * Finds the city by code.
     *
     * @param code the code
     * @return the city
     */
    public CityDto findByCode(final String code) {
        final City city = cityRepository.findByCode(code);
        if (city != null) {
            return cityModelMapper.toDto(city);
        }
        throw new CityNotFoundException(code);
    }

    /**
     * Finds the cities by name.
     *
     * @param name the name
     * @return the cities
     */
    public CityDtoList findByName(final String name) {
        final List<City> cities = cityRepository.findByName(name);
        if (CollectionUtils.isNotEmpty(cities)) {
            return CityDtoList.of(cityModelMapper.toDtos(cities));
        }
        throw new CityNotFoundException(name);
    }

    /**
     * Finds the cities by country.
     *
     * @param countryId the country id
     * @return the list of cities
     * @throws CountryNotFoundException in case the country is not found
     */
    public CityDtoList findByCountry(final Long countryId) throws CountryNotFoundException {
        final CountryDto countryDto = countryService.findById(countryId);
        if (countryDto == null) {
            log.error("Country not found for id: " + countryId);
            throw new CountryNotFoundException(countryId);
        }
        final List<City> cities = cityRepository.findByCountry(countryService.toCountry(countryDto));
        if (CollectionUtils.isNotEmpty(cities)) {
            return CityDtoList.of(cityModelMapper.toDtos(cities));
        }

        throw new CityServiceException("No cities found for country id: " + countryId);
    }

    /**
     * Finds the by country code.
     *
     * @param countryCode the country code
     * @return the list of cities
     */
    public CityDtoList findByCountryCode(final String countryCode) {
        final CountryDto countryDto = countryService.findByCode(countryCode);
        if (countryDto == null) {
            log.error("Country not found for id: " + countryCode);
            throw new CountryNotFoundException(countryCode);
        }
        final List<City> cities = cityRepository.findByCountryCode(countryCode);
        if (CollectionUtils.isNotEmpty(cities)) {
            return CityDtoList.of(cityModelMapper.toDtos(cities));
        }

        throw new CityServiceException("No cities found for country code: " + countryCode);
    }

    /**
     * Finds all cities.
     *
     * @return the list
     */
    public CityDtoList findAll() {
        final List<City> cities = cityRepository.findAll();
        if (CollectionUtils.isNotEmpty(cities)) {
            return CityDtoList.of(cityModelMapper.toDtos(cities));
        }
        throw new CityServiceException("No cities found.");
    }
}
