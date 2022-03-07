/*
* Id: AbstractDTO.java 14-Feb-2022 3:14:11 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The abstract data transfer object.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class Dto<T extends AbstractModel> {

    protected Long id;
}
