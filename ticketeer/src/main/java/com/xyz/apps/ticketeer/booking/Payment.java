/*
* Id: Payment.java 15-Feb-2022 3:15:57 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The Payment.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class Payment extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The booking. */
    @NotNull(message = "Booking cannot be null.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "bookingId", nullable = false)
    private Booking booking;

    /** The payment method. */
    @NotNull(message = "Payment method cannot be null.")
    @Column(nullable = false)
    private String paymentMethod;

    /** The transaction id. */
    @NotNull(message = "Transaction ID cannot be null.")
    @Column(nullable = false)
    private String transactionId;

    /** The payment status. */
    @NotNull(message = "Payment status cannot be null.")
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;
}
