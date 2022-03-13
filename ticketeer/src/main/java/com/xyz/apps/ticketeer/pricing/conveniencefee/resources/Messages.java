/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.resources;

import com.xyz.apps.ticketeer.util.StringUtil;

/**
 * The messages.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Messages {

    /**
     * Instantiates a new messages.
     */
    private Messages() {

    }


    /** The message error fee percentage required. */
    public static final String MESSAGE_ERROR_REQUIRED_FEE_PERCENTAGE = "{pricing.conveniencefee.message.error.required.feePercentage}";

    /** The message error updation time required. */
    public static final String MESSAGE_ERROR_REQUIRED_UPDATION_TIME = "{pricing.conveniencefee.message.error.required.updationTime}";

    /** The message error required convenience fee. */
    public static final String MESSAGE_ERROR_REQUIRED_PLATFORM_CONVENIENCE_FEE = StringUtil.MESSAGE_KEY + "pricing.conveniencefee.message.error.required.platformConvenienceFee";

    /** The message error required platform convenience fee id. */
    public static final String MESSAGE_ERROR_REQUIRED_PLATFORM_CONVENIENCE_FEE_ID = StringUtil.MESSAGE_KEY + "pricing.conveniencefee.message.error.required.platformConvenienceFee.id";

    /** The message error not found platform convenience fee. */
    public static final String MESSAGE_ERROR_NOT_FOUND_PLATFORM_CONVENIENCE_FEE = StringUtil.MESSAGE_KEY + "pricing.conveniencefee.message.error.notFound.platformConvenienceFee";

    /** The message error not found platform convenience fee id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_PLATFORM_CONVENIENCE_FEE_ID = StringUtil.MESSAGE_KEY + "pricing.conveniencefee.message.error.notFound.platformConvenienceFee.id";

    /** The message error failure add platform convenience fee. */
    public static final String MESSAGE_ERROR_FAILURE_ADD_PLATFORM_CONVENIENCE_FEE = StringUtil.MESSAGE_KEY + "pricing.conveniencefee.message.error.failure.add.platformConvenienceFee";

    /** The message error failure update platform convenience fee. */
    public static final String MESSAGE_ERROR_FAILURE_UPDATE_PLATFORM_CONVENIENCE_FEE = StringUtil.MESSAGE_KEY + "pricing.conveniencefee.message.error.failure.update.platformConvenienceFee";

}
