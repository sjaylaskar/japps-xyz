/*
 * Id: City.java 13-Feb-2022 5:34:55 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The city.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
@Validated
@Table(
    uniqueConstraints =
       {@UniqueConstraint(name = "UNIQUE_code", columnNames = "code")}
)
public class City extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The city id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @SequenceGenerator(initialValue = 1, name = "city_seq", allocationSize = 1)
    private Long id;

    /** The code. */
    @Column(unique = true, nullable = false, length = 10)
    @NotBlank(message = "City code is required.")
    @Size(min = 2, max = 10, message = "The city code must be between 2 and 10 characters.")
    private String code;

    /** The name. */
    @Column(nullable = false)
    @NotBlank(message = "City name is required.")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "countryId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "Country is required for City.")
    private Country country;
}
