/*
 * Id: CountryService.java 14-Feb-2022 1:19:40 am SubhajoyLaskar
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

import com.xyz.apps.ticketeer.general.model.DtoList;
import com.xyz.apps.ticketeer.general.service.GeneralService;


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
    public CountryDto add(@NotNull(message = "The country cannot be null.") final CountryDto countryDto) {

        return countryModelMapper.toDto(countryRepository.save(countryModelMapper.toEntity(countryDto)));
    }

    /**
     * Adds all.
     *
     * @param countries the countries
     * @return the list of countries
     */
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
    public CountryDto update(@NotNull(message = "The country cannot be null.") final CountryDto countryDto) {

        if (countryRepository.existsById(countryDto.getId())) {
            return countryModelMapper.toDto(countryRepository.save(countryModelMapper.toEntity(countryDto)));
        }

        throw new CountryNotFoundException(countryDto.getId());
    }

    /**
     * Delete.
     *
     * @param country the country
     */
    public void delete(@NotNull(message = "The country cannot be null.") final CountryDto countryDto) {

        if (countryRepository.existsById(countryDto.getId())) {
            countryRepository.delete(countryModelMapper.toEntity(countryDto));
        }
        throw new CountryNotFoundException(countryDto.getId());
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(final Long id) {

        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
        }
        throw new CountryNotFoundException(id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    public void deleteByCode(final String code) {

        final CountryDto countryDto = findByCode(code);
        if (countryDto != null) {
            countryRepository.deleteByCode(code);
        }
        throw new CountryNotFoundException(code);
    }

    /**
     * Delete by name.
     *
     * @param name the name
     */
    public void deleteByName(final String name) {

        final CountryDto countryDto = findByName(name);
        if (countryDto != null) {
            countryRepository.deleteByName(name);
        }
        throw new CountryNotFoundException(name);
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
