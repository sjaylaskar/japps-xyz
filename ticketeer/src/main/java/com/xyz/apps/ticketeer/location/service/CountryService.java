/*
 * Id: CountryService.java 14-Feb-2022 1:19:40 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.model.DtoList;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryCreationDto;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryCreationDtoList;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryDto;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryDtoList;
import com.xyz.apps.ticketeer.location.model.Country;
import com.xyz.apps.ticketeer.location.model.CountryCreationModelMapper;
import com.xyz.apps.ticketeer.location.model.CountryModelMapper;
import com.xyz.apps.ticketeer.location.model.CountryRepository;
import com.xyz.apps.ticketeer.location.resources.Messages;
import com.xyz.apps.ticketeer.util.StringUtil;


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

    /** The country creation model mapper. */
    @Autowired
    private CountryCreationModelMapper countryCreationModelMapper;

    /**
     * Adds the country.
     *
     * @param countryCreationDto the country creation dto
     * @return the country
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CountryDto add(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_COUNTRY) final CountryCreationDto countryCreationDto) {
        validateCountryCreation(countryCreationDto);
        final Country country = countryRepository.save(countryCreationModelMapper.toEntity(countryCreationDto));
        if (country == null) {
            throw new CountryServiceException(Messages.MESSAGE_ERROR_FAILED_COUNTRY_ADD);
        }
        return countryModelMapper.toDto(country);
    }

    /**
     * Adds all.
     *
     * @param countryCreationDtoList the country creation dto list
     * @return the list of countries
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CountryDtoList addAll(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_COUNTRY_LIST) final CountryCreationDtoList countryCreationDtoList) {

        if (DtoList.isNotEmpty(countryCreationDtoList)) {
            if (countryCreationDtoList.dtos().stream().map(CountryCreationDto::getCode).collect(Collectors.toSet()).size() != countryCreationDtoList.size()) {
                throw new CountryServiceException(Messages.MESSAGE_ERROR_UNIQUE_COUNTRY_CODES);
            }
            countryCreationDtoList.dtos().stream().forEach(this::validateCountryCreation);
            final List<Country> countries = countryRepository.saveAll(countryCreationModelMapper.toEntities(countryCreationDtoList.dtos()));
            if (CollectionUtils.isEmpty(countries)) {
                throw new CountryServiceException(Messages.MESSAGE_ERROR_FAILED_COUNTRIES_ADD);
            }
            return CountryDtoList.of(countryModelMapper.toDtos(countries));
        }

        throw new CountryServiceException(Messages.MESSAGE_ERROR_NOT_NULL_COUNTRY_LIST);
    }

    /**
     * Validate country creation.
     *
     * @param countryCreationDto the country creation dto
     */
    private void validateCountryCreation(final CountryCreationDto countryCreationDto) {

        if (countryRepository.findByCode(countryCreationDto.getCode()) != null) {
            throw new CountryAlreadyExistsException(countryCreationDto.getCode());
        }
    }

    /**
     * Updates the country.
     *
     * @param countryDto the country dto
     * @return the country
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CountryDto update(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_COUNTRY) final CountryDto countryDto) {
        if (countryDto.getId() == null) {
            throw new CountryServiceException(Messages.MESSAGE_ERROR_NOT_NULL_COUNTRY_ID);
        }
        if (!countryRepository.existsById(countryDto.getId())) {
            throw CountryNotFoundException.forId(countryDto.getId());
        }
        final Country countryByCode = countryRepository.findByCode(countryDto.getCode());
        if (countryByCode != null && countryByCode.getId() != countryDto.getId()) {
            throw new CountryAlreadyExistsException(countryDto.getCode());
        }
        return countryModelMapper.toDto(countryRepository.save(countryModelMapper.toEntity(countryDto)));
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteById(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_COUNTRY_ID) final Long id) {

        if (!countryRepository.existsById(id)) {
            throw CountryNotFoundException.forId(id);
        }
        countryRepository.deleteById(id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteByCode(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_BLANK_COUNTRY_CODE) final String code) {

        final CountryDto countryDto = findByCode(code);
        if (countryDto == null) {
            throw CountryNotFoundException.forCode(code);
        }

        countryRepository.deleteByCode(code);
    }


    /**
     * Finds the country by id.
     *
     * @param id the id
     * @return the country
     */
    public CountryDto findById(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_COUNTRY_ID) final Long id) {

        return countryModelMapper.toDto(
            countryRepository.findById(id).orElseThrow(() -> CountryNotFoundException.forId(id)));
    }

    /**
     * Finds the country by code.
     *
     * @param code the code
     * @return the country
     */
    public CountryDto findByCode(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_BLANK_COUNTRY_CODE) final String code) {

        final Country country = countryRepository.findByCode(code);
        if (country != null) {
            return countryModelMapper.toDto(country);
        }
        throw CountryNotFoundException.forCode(code);
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
        throw new CountryNotFoundException();
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
