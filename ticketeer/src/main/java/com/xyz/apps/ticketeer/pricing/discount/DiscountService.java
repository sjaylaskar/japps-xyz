/*
 * Id: DiscountService.java 05-Mar-2022 12:43:40 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.discount;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.xyz.apps.ticketeer.model.DtoList;
import com.xyz.apps.ticketeer.model.DtoListEmptyException;
import com.xyz.apps.ticketeer.util.MongoTemplate;


/**
 * The discount service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class DiscountService {

    /** The discount repository. */
    @Autowired
    private DiscountRepository discountRepository;

    /** The discount model mapper. */
    @Autowired
    private DiscountModelMapper discountModelMapper;

    /**
     * Adds the discount.
     *
     * @param discountDto the discount dto
     * @return the discount dto
     */
    public DiscountDto add(@NotNull(message = "The discount to add cannot be null.") final DiscountDto discountDto) {

        validateDetails(discountDto);

        final Discount discountAdded = discountRepository.save(discountModelMapper.toEntity(discountDto));

        if (discountAdded == null) {
            throw new DiscountAddFailedException(Arrays.asList(discountDto));
        }

        return discountModelMapper.toDto(discountAdded);
    }

    /**
     * Adds all the discounts.
     *
     * @param discountDtoList the discount dto list
     * @return the discount dto list
     */
    public DiscountDtoList addAll(@NotNull(
        message = "The discounts list to add cannot be null."
    ) final DiscountDtoList discountDtoList) {

        if (DtoList.isEmpty(discountDtoList)) {
            throw new DtoListEmptyException("The discount list to add cannot be null or empty.");
        }

        discountDtoList.dtos().stream().forEach(this::validateDetails);

        final List<Discount> discountsAdded = discountRepository.saveAll(discountModelMapper.toEntities(discountDtoList.dtos()));

        if (CollectionUtils.isEmpty(discountsAdded)) {
            throw new DiscountAddFailedException(discountDtoList.dtos());
        }

        return DiscountDtoList.of(discountModelMapper.toDtos(discountsAdded));
    }

    /**
     * Updates discount.
     *
     * @param discountDto the discount dto
     * @return the discount dto
     */
    public DiscountDto update(@NotNull(message = "The discount to update cannot be null.") final DiscountDto discountDto) {

        validateDiscountIdNotNull(discountDto);

        validateDiscountExists(discountDto);

        validateDetails(discountDto);

        final Discount discountUpdated = discountRepository.save(discountModelMapper.toEntity(discountDto));

        if (discountUpdated == null) {
            throw new DiscountAddFailedException(Arrays.asList(discountDto));
        }

        return discountModelMapper.toDto(discountUpdated);

    }

    /**
     * Updates all discount.
     *
     * @param discountDtoList the discount dto list
     * @return the discount dto list
     */
    public DiscountDtoList updateAll(@NotNull(
        message = "The discounts list to update cannot be null."
    ) final DiscountDtoList discountDtoList) {

        if (DtoList.isEmpty(discountDtoList)) {
            throw new DtoListEmptyException("The discount list to add cannot be null or empty.");
        }

        discountDtoList.dtos().stream().forEach(this::validateDiscountIdNotNull);

        discountDtoList.dtos().stream().forEach(this::validateDiscountExists);

        discountDtoList.dtos().stream().forEach(this::validateDetails);

        final List<Discount> discountsUpdated = discountRepository.saveAll(discountModelMapper.toEntities(discountDtoList.dtos()));

        if (CollectionUtils.isEmpty(discountsUpdated)) {
            throw new DiscountAddFailedException(discountDtoList.dtos());
        }

        return DiscountDtoList.of(discountModelMapper.toDtos(discountsUpdated));
    }

    /**
     * Delete by offer code.
     *
     * @param offerCode the offer code
     */
    public void deleteByOfferCode(@NotBlank(message = "The discount offer code to delete cannot be blank.") final String offerCode) {

        final Discount discount = findDiscountByOfferCode(offerCode);

        if (discount == null) {
            throw new DiscountNotFoundException(offerCode);
        }

        discountRepository.delete(discount);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(@NotNull(message = "The discount id to delete cannot be null.") final Long id) {

        validateDiscountExistsById(id);

        discountRepository.deleteById(id);
    }

    /**
     * Validate discount id not null.
     *
     * @param discountDto the discount dto
     * @return the long
     */
    private Long validateDiscountIdNotNull(final DiscountDto discountDto) {

        return Objects.requireNonNull(discountDto.getId(),
            "The discount id to update cannot be null");
    }

    /**
     * Validate discount exists.
     *
     * @param discountDto the discount dto
     */
    private void validateDiscountExists(final DiscountDto discountDto) {

        validateDiscountExistsById(discountDto.getId());
    }

    /**
     * Validate discount exists by id.
     *
     * @param discountDto the discount dto
     */
    private void validateDiscountExistsById(final Long id) {

        if (!discountRepository.existsById(id)) {
            throw new DiscountNotFoundException(id);
        }
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the discount dto
     */
    public DiscountDto findById(@NotNull(message = "The discount id to delete cannot be null.") final Long id) {
        return discountModelMapper.toDto(discountRepository.findById(id).orElseThrow(() -> new DiscountNotFoundException(id)));
    }

    /**
     * Finds the by offer code.
     *
     * @param offerCode the offer code
     * @return the discount dto
     */
    public DiscountDto findByOfferCode(@NotBlank(message = "The offer code cannot be blank.") final String offerCode) {

        final Discount discount = findDiscountByOfferCode(offerCode);

        if (discount == null) {
            throw new DiscountNotFoundException(offerCode);
        }

        return discountModelMapper.toDto(discount);
    }

    /**
     * Finds the discount by offer code.
     *
     * @param offerCode the offer code
     * @return the discount
     */
    private Discount findDiscountByOfferCode(final String offerCode) {

        return MongoTemplate.get().findOne(new Query().addCriteria(Criteria.where("offerCode").is(offerCode)),
            Discount.class);
    }

    /**
     * Validate.
     *
     * @param discountDto the discount dto
     */
    private void validateDetails(final DiscountDto discountDto) {

        if (CollectionUtils.isNotEmpty(discountDto.getApplicableCityIds())) {
            validateApplicableCityIds(discountDto.getApplicableCityIds());
        }

        if (CollectionUtils.isNotEmpty(discountDto.getApplicableEventVenueIds())) {
            validateApplicableEventVenueIds(discountDto.getApplicableEventVenueIds());
        }
    }

    /**
     * Validate applicable city ids.
     *
     * @param applicableCityIds the applicable city ids
     */
    private void validateApplicableCityIds(final Set<Long> applicableCityIds) {
        // TODO
    }

    /**
     * Validate applicable event venue ids.
     *
     * @param applicableEventVenueIds the applicable event venue ids
     */
    private void validateApplicableEventVenueIds(final Set<Long> applicableEventVenueIds) {
        // TODO
    }
}
