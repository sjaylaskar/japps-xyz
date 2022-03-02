/*
* Id: AuditoriumDetailsDto.java 02-Mar-2022 12:09:52 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The auditorium details dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class AuditoriumDetailsDto {

    /** The name. */
    private String name;

    /** The number of rows. */
    private int numberOfRows;

    /** The seats per row. */
    private int seatsPerRow;
}
