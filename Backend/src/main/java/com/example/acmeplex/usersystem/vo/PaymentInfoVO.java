package com.example.acmeplex.usersystem.vo;

import jakarta.validation.constraints.NotNull;

/**
 * Generic interface for payment information.
 */
public interface PaymentInfoVO {

    /**
     * Retrieves the type of payment.
     *
     * @return the payment type
     */
    @NotNull
    PaymentType getPaymentType();
}
