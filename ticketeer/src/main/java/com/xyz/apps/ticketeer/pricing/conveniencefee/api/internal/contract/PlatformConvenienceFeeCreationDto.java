/*
* Id: PlatformConvenienceFee.java 03-Mar-2022 11:26:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.Dto;
import com.xyz.apps.ticketeer.pricing.conveniencefee.model.PlatformConvenienceFee;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The platform convenience fee creation dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformConvenienceFeeCreationDto extends Dto<PlatformConvenienceFee> {

    /** The fee percentage. */
    private Double feePercentage = 0d;
}
