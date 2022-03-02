/*
* Id: EventShowSearchCriteria.java 03-Mar-2022 12:28:40 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The event show search criteria.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventShowSearchCriteria {

    /** The city id. */
    private Long cityId;

    /** The event id. */
    private Long eventId;

    /** The date. */
    private String date;
}
