/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.general.AbstractModelMapper;
import com.xyz.apps.ticketeer.pricing.calculator.DiscountStrategy;
import com.xyz.apps.ticketeer.pricing.calculator.ShowTimeType;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;


/**
 * The discount dto mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class DiscountModelMapper extends AbstractModelMapper<Discount, DiscountDto> {

    public DiscountModelMapper() {

        super(Discount.class, DiscountDto.class);

        initMappings();
    }

    private void initMappings() {
        final TypeMap<Discount, DiscountDto> discountToDiscountDtoMap = modelMapper.createTypeMap(Discount.class, DiscountDto.class);
        discountToDiscountDtoMap
        .addMappings(
          mapper -> mapper.map(discount -> discount.getDiscountStrategy().name(), DiscountDto::setDiscountStrategy)
        )
        .addMappings(
            mapper -> mapper.map(discount -> discount.getDiscountType().name(), DiscountDto::setDiscountType)
          )
        .addMappings(
            mapper -> mapper.map(discount -> discount.getShowTimeType().name(), DiscountDto::setShowTimeType)
          )
        .addMappings(
            mapper -> mapper.map(discount -> LocalDateTimeFormatUtil.format(discount.getStartTime()), DiscountDto::setStartTime)
          )
        .addMappings(
            mapper -> mapper.map(discount -> LocalDateTimeFormatUtil.format(discount.getEndTime()), DiscountDto::setEndTime)
          );

        final TypeMap<DiscountDto, Discount> discountDtoToDiscountMap = modelMapper.createTypeMap(DiscountDto.class, Discount.class);
        discountDtoToDiscountMap
        .addMappings(
          mapper -> mapper.map(discountDto -> DiscountStrategy.of(discountDto.getDiscountStrategy()), Discount::setDiscountStrategy)
        )
        .addMappings(
            mapper -> mapper.map(discountDto -> DiscountType.of(discountDto.getDiscountType()), Discount::setDiscountType)
          )
        .addMappings(
            mapper -> mapper.map(discountDto -> ShowTimeType.of(discountDto.getShowTimeType()), Discount::setShowTimeType)
          )
        .addMappings(
            mapper -> mapper.map(discountDto -> LocalDateTimeFormatUtil.parseLocalDateTime(discountDto.getStartTime()), Discount::setStartTime)
          )
        .addMappings(
            mapper -> mapper.map(discountDto -> LocalDateTimeFormatUtil.parseLocalDateTime(discountDto.getEndTime()), Discount::setEndTime)
          );
    }


}
