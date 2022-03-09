/*
* Id: PlatformConvenienceFeeService.java 05-Mar-2022 4:30:46 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.service;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.pricing.conveniencefee.api.internal.contract.PlatformConvenienceFeeDto;
import com.xyz.apps.ticketeer.pricing.conveniencefee.model.PlatformConvenienceFee;
import com.xyz.apps.ticketeer.pricing.conveniencefee.model.PlatformConvenienceFeeModelMapper;
import com.xyz.apps.ticketeer.pricing.conveniencefee.model.PlatformConvenienceFeeRepository;

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

    /**
     * Adds the platform convenience fee.
     *
     * @param platformConvenienceFeeDto the platform convenience fee dto
     * @return the platform convenience fee dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public PlatformConvenienceFeeDto add(@NotNull(message = "The platform convenience fee to add cannot be null.") final PlatformConvenienceFeeDto platformConvenienceFeeDto) {
        final PlatformConvenienceFee platformConvenienceFee = platformConvenienceFeeRepository.save(platformConvenienceFeeModelMapper.toEntity(platformConvenienceFeeDto));

        if (platformConvenienceFee == null) {
            throw new PlatformConvenienceFeeAddFailedException();
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
    public PlatformConvenienceFeeDto update(@NotNull(message = "The platform convenience fee to update cannot be null.") final PlatformConvenienceFeeDto platformConvenienceFeeDto) {
        final PlatformConvenienceFee platformConvenienceFee = find();
        platformConvenienceFee.setFeePercentage(platformConvenienceFeeDto.getFeePercentage());
        platformConvenienceFee.setUpdationTime(LocalDateTime.now());

        final PlatformConvenienceFee platformConvenienceFeeUpdated = platformConvenienceFeeRepository.save(platformConvenienceFee);

        if (platformConvenienceFeeUpdated == null) {
            throw new PlatformConvenienceFeeUpdateFailedException();
        }

        return platformConvenienceFeeModelMapper.toDto(platformConvenienceFee);
    }

    /**
     * Finds the platform convenience fee.
     *
     * @return the platform convenience fee
     */
    private PlatformConvenienceFee find() {

        final PlatformConvenienceFee platformConvenienceFee = serviceBeansFetcher().mongoTemplate().findOne(new Query().with(Sort.by(Order.desc("updationTime"))).with(PageRequest.of(0, 1)),
                                    PlatformConvenienceFee.class);
        if (platformConvenienceFee == null) {
            throw new PlatformConvenienceFeeNotFoundException();
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
    public void deleteById(final String id) {
        if (!platformConvenienceFeeRepository.existsById(new ObjectId(id))) {
            throw new PlatformConvenienceFeeNotFoundException(id);
        }
        platformConvenienceFeeRepository.deleteById(new ObjectId(id));
    }
}
