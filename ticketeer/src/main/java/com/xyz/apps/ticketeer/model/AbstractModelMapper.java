/*
* Id: AbstractModelMapper.java 14-Feb-2022 6:40:37 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The abstract model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public abstract class AbstractModelMapper {

    /** The model mapper. */
    @Autowired
    protected ModelMapper modelMapper;

}
