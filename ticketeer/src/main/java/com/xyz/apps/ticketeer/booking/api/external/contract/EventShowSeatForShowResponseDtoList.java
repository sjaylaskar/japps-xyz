/*
 * Id: EventShowSeatForShowResponseDtoList.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.api.external.contract;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show seat for show response dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventShowSeatForShowResponseDtoList {

    /** The id. */
    private Long eventShowId;

    /** The dtos. */
    private List<EventShowSeatForShowResponseDto> dtos = new ArrayList<>();

}
