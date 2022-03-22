/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee.resources;

import java.util.ResourceBundle;

import com.xyz.apps.ticketeer.util.MessageUtil;

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

    /** The error message prefix. */
    private static final String ERROR_MESSAGE_PREFIX = "pricing.conveniencefee.message.error.";

    /*
     * Model messages:
     */

    /** The message error fee percentage required. */
    public static final String MESSAGE_ERROR_REQUIRED_FEE_PERCENTAGE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.feePercentage" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error updation time required. */
    public static final String MESSAGE_ERROR_REQUIRED_UPDATION_TIME = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.updationTime" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /*
     * Other messages:
     */

    /** The message error required convenience fee. */
    public static final String MESSAGE_ERROR_REQUIRED_PLATFORM_CONVENIENCE_FEE = ERROR_MESSAGE_PREFIX + "required.platformConvenienceFee";

    /** The message error required platform convenience fee id. */
    public static final String MESSAGE_ERROR_REQUIRED_PLATFORM_CONVENIENCE_FEE_ID = ERROR_MESSAGE_PREFIX + "required.platformConvenienceFee.id";

    /** The message error not found platform convenience fee. */
    public static final String MESSAGE_ERROR_NOT_FOUND_PLATFORM_CONVENIENCE_FEE = ERROR_MESSAGE_PREFIX + "notFound.platformConvenienceFee";

    /** The message error not found platform convenience fee id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_PLATFORM_CONVENIENCE_FEE_ID = ERROR_MESSAGE_PREFIX + "notFound.platformConvenienceFee.id";

    /** The message error failure add platform convenience fee. */
    public static final String MESSAGE_ERROR_FAILURE_ADD_PLATFORM_CONVENIENCE_FEE = ERROR_MESSAGE_PREFIX + "failure.add.platformConvenienceFee";

    /** The message error failure update platform convenience fee. */
    public static final String MESSAGE_ERROR_FAILURE_UPDATE_PLATFORM_CONVENIENCE_FEE = ERROR_MESSAGE_PREFIX + "failure.update.platformConvenienceFee";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return MessageUtil.resourceBundle("module.pricing.conveniencefee.Messages");
    }
}
