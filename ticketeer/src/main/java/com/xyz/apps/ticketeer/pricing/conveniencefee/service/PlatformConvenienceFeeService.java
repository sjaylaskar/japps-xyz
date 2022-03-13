/*
 * Id: PlatformConvenienceFeeService.java 05-Mar-2022 4:30:46 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.conveniencefee.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.pricing.conveniencefee.api.internal.contract.PlatformConvenienceFeeCreationDto;
import com.xyz.apps.ticketeer.pricing.conveniencefee.api.internal.contract.PlatformConvenienceFeeDto;
import com.xyz.apps.ticketeer.pricing.conveniencefee.model.PlatformConvenienceFee;
import com.xyz.apps.ticketeer.pricing.conveniencefee.model.PlatformConvenienceFeeCreationModelMapper;
import com.xyz.apps.ticketeer.pricing.conveniencefee.model.PlatformConvenienceFeeModelMapper;
import com.xyz.apps.ticketeer.pricing.conveniencefee.model.PlatformConvenienceFeeRepository;
import com.xyz.apps.ticketeer.pricing.conveniencefee.resources.Messages;
import com.xyz.apps.ticketeer.util.MessageUtil;

import lombok.extern.log4j.Log4j2;


/**
 * The platform convenience fee service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Validated
@Service
@Log4j2
public class PlatformConvenienceFeeService extends GeneralService {

    /** The platform convenience fee repository. */
    @Autowired
    private PlatformConvenienceFeeRepository platformConvenienceFeeRepository;

    /** The platform convenience fee model mapper. */
    @Autowired
    private PlatformConvenienceFeeModelMapper platformConvenienceFeeModelMapper;

    /** The platform convenience fee creation model mapper. */
    @Autowired
    private PlatformConvenienceFeeCreationModelMapper platformConvenienceFeeCreationModelMapper;

    /**
     * Adds the platform convenience fee.
     *
     * @param platformConvenienceFeeCreationDto the platform convenience fee dto
     * @return the platform convenience fee dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public PlatformConvenienceFeeDto add(@NotNull(
        message = Messages.MESSAGE_ERROR_REQUIRED_PLATFORM_CONVENIENCE_FEE
    ) final PlatformConvenienceFeeCreationDto platformConvenienceFeeCreationDto) {

        final PlatformConvenienceFee platformConvenienceFee = platformConvenienceFeeRepository.save(
            platformConvenienceFeeCreationModelMapper.toEntity(platformConvenienceFeeCreationDto));

        if (platformConvenienceFee == null) {
            throw new PlatformConvenienceFeeAddFailedException(messageSource());
        }

        return platformConvenienceFeeModelMapper.toDto(platformConvenienceFee);
    }

    /**
     * Updates the platform convenience fee.
     *
     * @param platformConvenienceFeeDto the platform convenience fee dto
     * @return the platform convenience fee dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public PlatformConvenienceFeeDto update(@NotNull(
        message = Messages.MESSAGE_ERROR_REQUIRED_PLATFORM_CONVENIENCE_FEE
    ) final PlatformConvenienceFeeDto platformConvenienceFeeDto) {

        if (existsById(platformConvenienceFeeDto.getId())) {
            final PlatformConvenienceFee platformConvenienceFeeUpdated = platformConvenienceFeeRepository.save(
                platformConvenienceFeeModelMapper.toEntity(platformConvenienceFeeDto));

            if (platformConvenienceFeeUpdated == null) {
                throw new PlatformConvenienceFeeUpdateFailedException(messageSource());
            }

            return platformConvenienceFeeModelMapper.toDto(platformConvenienceFeeUpdated);
        }
        throw new PlatformConvenienceFeeUpdateFailedException(messageSource());
    }

    /**
     * Exists by id.
     *
     * @param id the id
     * @return true, if successful
     */
    private boolean existsById(final String id) {
        if (StringUtils.isBlank(id)) {
            throw new PlatformConvenienceFeeServiceException(MessageUtil.defaultLocaleMessage(messageSource(),
                Messages.MESSAGE_ERROR_REQUIRED_PLATFORM_CONVENIENCE_FEE_ID, null));
        }
        if (!platformConvenienceFeeRepository.existsById(new ObjectId(id))) {
            throw new PlatformConvenienceFeeNotFoundException(messageSource(), id);
        }
        return true;
    }

    /**
     * Finds the platform convenience fee.
     *
     * @return the platform convenience fee
     */
    private PlatformConvenienceFee find() {

        final PlatformConvenienceFee platformConvenienceFee = serviceBeansFetcher().mongoTemplate().findOne(new Query().with(Sort
            .by(Order.desc("updationTime"))).with(PageRequest.of(0, 1)),
            PlatformConvenienceFee.class);
        if (platformConvenienceFee == null) {
            throw new PlatformConvenienceFeeNotFoundException(messageSource());
        }
        return platformConvenienceFee;
    }

    /**
     * Finds the platform convenience fee percentage.
     *
     * @return the platform convenience fee percentage.
     */
    public Double findPercentage() {

        try {
            return find().getFeePercentage();
        } catch (final Exception exception) {
            log.error(exception);
            return 0d;
        }
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteById(@NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_PLATFORM_CONVENIENCE_FEE_ID) final String id) {

        if (existsById(id)) {
            platformConvenienceFeeRepository.deleteById(new ObjectId(id));
        }
    }

    /**
     * Message source.
     *
     * @return the message source
     */
    private MessageSource messageSource() {

        return serviceBeansFetcher().messageSource();
    }
}
