/*
* Id: Event.java 15-Feb-2022 3:00:51 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.event.resources.Messages;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The Event.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
@Validated
public class Event extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(initialValue = 1, name = "event_seq", allocationSize = 1)
    private Long id;

    /** The name. */
    @Column(nullable = false)
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_NAME)
    private String name;
}
