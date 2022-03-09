/*
 * Id: CountryService.java 14-Feb-2022 1:19:40 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.model.DtoList;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryDto;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryDtoList;
import com.xyz.apps.ticketeer.location.model.Country;
import com.xyz.apps.ticketeer.location.model.CountryModelMapper;
import com.xyz.apps.ticketeer.location.model.CountryRepository;


/**
 * The country service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class CountryService extends GeneralService {

    /** The country repository. */
    @Autowired
    private CountryRepository countryRepository;

    /** The country model mapper. */
    @Autowired
    private CountryModelMapper countryModelMapper;

    /**
     * Adds the country.
     *
     * @param country the country
     * @return the country
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CountryDto add(@NotNull(message = "The country cannot be null.") final CountryDto countryDto) {

        return countryModelMapper.toDto(countryRepository.save(countryModelMapper.toEntity(countryDto)));
    }

    /**
     * Adds all.
     *
     * @param countries the countries
     * @return the list of countries
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CountryDtoList addAll(@NotNull(message = "The country list cannot be null.") final CountryDtoList countryDtoList) {

        if (DtoList.isNotEmpty(countryDtoList)) {
            return CountryDtoList.of(countryModelMapper.toDtos(countryRepository.saveAll(countryModelMapper.toEntities(
                countryDtoList.dtos()))));
        }

        throw new CountryServiceException("The country list cannot be empty.");
    }

    /**
     * Updates the country.
     *
     * @param country the country
     * @return the country
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CountryDto update(@NotNull(message = "The country cannot be null.") final CountryDto countryDto) {

        if (!countryRepository.existsById(countryDto.getId())) {
            throw new CountryNotFoundException(countryDto.getId());
        }
        return countryModelMapper.toDto(countryRepository.save(countryModelMapper.toEntity(countryDto)));
    }

    /**
     * Delete.
     *
     * @param country the country
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void delete(@NotNull(message = "The country cannot be null.") final CountryDto countryDto) {

        if (!countryRepository.existsById(countryDto.getId())) {
            throw new CountryNotFoundException(countryDto.getId());
        }
        countryRepository.delete(countryModelMapper.toEntity(countryDto));
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteById(final Long id) {

        if (!countryRepository.existsById(id)) {
            throw new CountryNotFoundException(id);
        }
        countryRepository.deleteById(id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteByCode(final String code) {

        final CountryDto countryDto = findByCode(code);
        if (countryDto == null) {
            throw new CountryNotFoundException(code);
        }

        countryRepository.deleteByCode(code);
    }

    /**
     * Delete by name.
     *
     * @param name the name
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteByName(final String name) {

        final CountryDto countryDto = findByName(name);
        if (countryDto == null) {
            throw new CountryNotFoundException(name);
        }
        countryRepository.deleteByName(name);
    }

    /**
     * Finds the country by id.
     *
     * @param id the id
     * @return the country
     */
    public CountryDto findById(final Long id) {

        return countryModelMapper.toDto(
            countryRepository.findById(id).orElseThrow(() -> new CountryNotFoundException(id)));
    }

    /**
     * Finds the country by code.
     *
     * @param code the code
     * @return the country
     */
    public CountryDto findByCode(final String code) {

        final Country country = countryRepository.findByCode(code);
        if (country != null) {
            return countryModelMapper.toDto(country);
        }
        throw new CountryNotFoundException(code);
    }

    /**
     * Finds the country by name.
     *
     * @param name the name
     * @return the country
     */
    public CountryDto findByName(final String name) {
        final Country country = countryRepository.findByName(name);
        if (country != null) {
            return countryModelMapper.toDto(country);
        }
        throw new CountryNotFoundException(name);
    }

    /**
     * Finds all countries.
     *
     * @return the list
     */
    public CountryDtoList findAll() {

        final List<Country> countries = countryRepository.findAll();
        if (CollectionUtils.isNotEmpty(countries)) {
            return CountryDtoList.of(countryModelMapper.toDtos(countries));
        }
        throw new CountryServiceException("No countries found.");
    }

    /**
     * To country.
     *
     * @param countryDto the country dto
     * @return the country
     */
    public Country toCountry(final CountryDto countryDto) {
        return countryModelMapper.toEntity(countryDto);
    }
}