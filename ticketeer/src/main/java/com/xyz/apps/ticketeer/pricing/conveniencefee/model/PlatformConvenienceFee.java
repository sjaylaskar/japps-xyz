/*
* Id: PlatformConvenienceFee.java 03-Mar-2022 11:26:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.validation.annotation.Validated;

import com.mongodb.lang.NonNull;
import com.xyz.apps.ticketeer.pricing.conveniencefee.resources.Messages;

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
@Validated
public class PlatformConvenienceFee extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @MongoId
    private ObjectId id;

    /** The percentage of ticket amount. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_FEE_PERCENTAGE)
    private Double feePercentage = 0d;

    /** The updatation time. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_UPDATION_TIME)
    private LocalDateTime updationTime = LocalDateTime.now();
}
