/*
* Id: PlatformConvenienceFee.java 03-Mar-2022 11:26:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee;

import com.xyz.apps.ticketeer.general.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The platform convenience fee dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class PlatformConvenienceFeeDto extends Dto<PlatformConvenienceFee> {

    /** The id. */
    private String id;

    /** The fee percentage. */
    private Double feePercentage = 0d;
}
