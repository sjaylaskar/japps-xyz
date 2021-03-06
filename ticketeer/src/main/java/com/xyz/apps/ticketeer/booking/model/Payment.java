/*
* Id: Payment.java 15-Feb-2022 3:15:57 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.annotation.Validated;

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
@Validated
@Table(
    uniqueConstraints =
       {@UniqueConstraint(name = "UNIQUE_txn_id", columnNames = "transactionId")}
)
public class Payment extends com.xyz.apps.ticketeer.general.model.Entity {

    // @TODO - Connect with actual payment gateway interface.
    /** The default payment method. */
    private static final String DEFAULT_PAYMENT_METHOD = "UPI";

    /** The default transaction id. */
    public static final String DEFAULT_TRANSACTION_ID_PREFIX = "TICKTEER";

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(initialValue = 1, name = "payment_seq", allocationSize = 1)
    private Long id;

    @NotNull(message = "Payment amount cannot be null.")
    @Column(nullable = false)
    private Double amount;

    /** The booking. */
    @NotNull(message = "Booking cannot be null.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "bookingId", nullable = false, foreignKey = @ForeignKey(name = "FK_Booking"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Booking booking;

    /** The payment method. */
    @NotNull(message = "Payment method cannot be null.")
    @Column(nullable = false)
    private String paymentMethod = DEFAULT_PAYMENT_METHOD;

    /** The transaction id. */
    @NotNull(message = "Transaction ID cannot be null.")
    @Column(nullable = false, unique = true)
    private String transactionId;

    /** The payment status. */
    @NotNull(message = "Payment status cannot be null.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
