/*
* Id: Event.java 15-Feb-2022 3:00:51 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import com.xyz.apps.ticketeer.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventDto extends Dto<Event> {

    /** The name. */
    private String name;
}