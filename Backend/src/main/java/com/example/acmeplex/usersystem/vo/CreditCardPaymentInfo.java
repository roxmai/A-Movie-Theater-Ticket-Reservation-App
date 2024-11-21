package com.example.acmeplex.usersystem.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Value Object representing credit card payment information.
 */
public class CreditCardPaymentInfo implements PaymentInfoVO {

    @NotBlank(message = "Card Number is mandatory")
    @Pattern(regexp = "\\d{16}", message = "Card Number must be 16 digits")
    private String cardNumber;

    @NotBlank(message = "Expiry Date is mandatory")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiry Date must be in MM/YY format")
    private String expiryDate;

    @NotBlank(message = "CVV is mandatory")
    @Pattern(regexp = "\\d{3}", message = "CVV must be 3 digits")
    private String cvv;

    @NotBlank(message = "Cardholder name is mandatory")
    @Size(max = 100, message = "Cardholder name must not exceed 100 characters")
    private String cardholderName;

    // Constructors

    /**
     * Default constructor required for frameworks like Spring.
     */
    public CreditCardPaymentInfo() {}

    /**
     * Parameterized constructor for creating instances with all fields set.
     *
     * @param cardNumber the credit card number
     * @param expiryDate the expiry date in MM/YY format
     * @param cvv the 3-digit CVV code
     * @param cardholderName the name of the cardholder
     */
    public CreditCardPaymentInfo(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardholderName = cardholderName;
    }

    // Getters and Setters

    /**
     * Retrieves the credit card number.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the credit card number.
     *
     * @param cardNumber the card number to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Retrieves the expiry date of the credit card.
     *
     * @return the expiry date in MM/YY format
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the credit card.
     *
     * @param expiryDate the expiry date to set in MM/YY format
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Retrieves the CVV code of the credit card.
     *
     * @return the 3-digit CVV code
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the CVV code of the credit card.
     *
     * @param cvv the 3-digit CVV code to set
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CREDIT_CARD;
    }

    @Override
    public String toString() {
        return "CreditCardVO{" +
                "cardNumber='" + maskCardNumber(cardNumber) + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='***'" + // Mask CVV for security
                '}';
    }

    /**
     * Masks the credit card number for security purposes.
     * Shows only the last four digits.
     *
     * @param cardNumber the original card number
     * @return the masked card number
     */
    private String maskCardNumber(String cardNumber) {
        if (cardNumber != null && cardNumber.length() == 16) {
            return "**** **** **** " + cardNumber.substring(12);
        }
        return "Invalid Card Number";
    }
}
