/*
 * Id: DiscountCreationModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service.modelmapper;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountCreationDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.Discount;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountStrategy;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountType;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.ShowTimeType;


/**
 * The discount creation model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class DiscountCreationModelMapper extends GeneralModelMapper<Discount, DiscountCreationDto> {

    /**
     * Instantiates a new discount creation model mapper.
     */
    public DiscountCreationModelMapper() {

        super(Discount.class, DiscountCreationDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {
        final Converter<String, DiscountStrategy> stringToDiscountStrategyConverter = converter -> (StringUtils.isNotBlank(converter.getSource())) ? DiscountStrategy.of(converter.getSource()) : null;
        final Converter<String, DiscountType> stringToDiscountTypeConverter = converter -> (StringUtils.isNotBlank(converter.getSource())) ? DiscountType.of(converter.getSource()) : null;
        final Converter<String, ShowTimeType> stringToShowTimeTypeConverter = converter -> (StringUtils.isNotBlank(converter.getSource())) ? ShowTimeType.of(converter.getSource()) : null;
        final TypeMap<DiscountCreationDto, Discount> discountDtoToDiscountMap = modelMapper.createTypeMap(DiscountCreationDto.class, Discount.class);
        discountDtoToDiscountMap
        .addMappings(
          mapper -> mapper.using(stringToDiscountStrategyConverter).map(DiscountCreationDto::getDiscountStrategy, Discount::setDiscountStrategy)
        )
        .addMappings(
            mapper -> mapper.using(stringToDiscountTypeConverter).map(DiscountCreationDto::getDiscountType, Discount::setDiscountType)
          )
        .addMappings(
            mapper -> mapper.using(stringToShowTimeTypeConverter).map(DiscountCreationDto::getShowTimeType, Discount::setShowTimeType)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATETIME_CONVERTER).map(DiscountCreationDto::getStartTime, Discount::setStartTime)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATETIME_CONVERTER).map(DiscountCreationDto::getEndTime, Discount::setEndTime)
          );
    }
}
