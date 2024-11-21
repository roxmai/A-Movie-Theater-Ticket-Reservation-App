package com.example.acmeplex.usersystem.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Value Object representing debit card payment information.
 */
public class DebitCardPaymentInfo implements PaymentInfoVO {

    /**
     * The debit card number.
     * Must be exactly 16 digits.
     */
    @NotBlank(message = "Card Number is mandatory")
    @Pattern(regexp = "\\d{16}", message = "Card Number must be 16 digits")
    private String cardNumber;

    /**
     * The expiry date of the debit card.
     * Must follow the MM/YY format.
     */
    @NotBlank(message = "Expiry Date is mandatory")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiry Date must be in MM/YY format")
    private String expiryDate;

    /**
     * The CVV code of the debit card.
     * Must be exactly 3 digits.
     * 
     * <strong>Note:</strong> Storing CVV is against PCI DSS compliance.
     * Consider handling CVV securely and avoid storing it if possible.
     */
    @NotBlank(message = "CVV is mandatory")
    @Pattern(regexp = "\\d{3}", message = "CVV must be 3 digits")
    private String cvv;

    /**
     * The name of the cardholder.
     */
    @NotBlank(message = "Cardholder name is mandatory")
    @Size(max = 100, message = "Cardholder name must not exceed 100 characters")
    private String cardholderName;

    // Constructors

    /**
     * Default constructor required for frameworks like Spring.
     */
    public DebitCardPaymentInfo() {}

    /**
     * Parameterized constructor for creating instances with all fields set.
     *
     * @param cardNumber     the debit card number
     * @param expiryDate     the expiry date in MM/YY format
     * @param cvv            the 3-digit CVV code
     * @param cardholderName the name of the cardholder
     */
    public DebitCardPaymentInfo(String cardNumber, String expiryDate, String cvv, String billingAddress, String cardholderName) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardholderName = cardholderName;
    }

    // Getters and Setters

    /**
     * Retrieves the debit card number.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the debit card number.
     *
     * @param cardNumber the card number to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Retrieves the expiry date of the debit card.
     *
     * @return the expiry date in MM/YY format
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the debit card.
     *
     * @param expiryDate the expiry date to set in MM/YY format
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Retrieves the CVV code of the debit card.
     *
     * @return the 3-digit CVV code
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the CVV code of the debit card.
     *
     * @param cvv the 3-digit CVV code to set
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }


    /**
     * Retrieves the name of the cardholder.
     *
     * @return the cardholder's name
     */
    public String getCardholderName() {
        return cardholderName;
    }

    /**
     * Sets the name of the cardholder.
     *
     * @param cardholderName the cardholder's name to set
     */
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    // Optional: Override toString to mask sensitive information

    @Override
    public String toString() {
        return "DebitCardVO{" +
                "cardNumber='" + maskCardNumber(cardNumber) + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='***'" + // Mask CVV for security
                ", cardholderName='" + cardholderName + '\'' +
                '}';
    }

    /**
     * Masks the debit card number for security purposes.
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