/*
* Id: PlatformConvenienceFee.java 03-Mar-2022 11:26:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The platform convenience fee.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Document("platformConvenienceFees")
@Getter
@Setter
@ToString
public class PlatformConvenienceFee extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @MongoId
    private Long id;

    /** The percentage of ticket amount. */
    @NonNull
    @NotNull(message = "The fee percentage cannot be null.")
    private Double feePercentage = 0d;

    /** The updatation time. */
    @NonNull
    @NotNull(message = "The fee updation time cannot be null.")
    private LocalDateTime updationTime = LocalDateTime.now();
}
