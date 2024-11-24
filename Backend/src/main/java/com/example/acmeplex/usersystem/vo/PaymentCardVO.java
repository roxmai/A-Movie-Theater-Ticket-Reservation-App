package com.example.acmeplex.usersystem.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

/**
 * Value Object representing general payment card information.
 * Supports both debit and credit cards for transactions.
 */
public class PaymentCardVO {

    /**
     * Enum representing the type of payment card.
     */
    public enum CardType {
        DEBIT, CREDIT
    }

    /**
     * The type of the payment card (DEBIT or CREDIT).
     */
    @NotNull(message = "Card Type is mandatory")
    private CardType cardType;

    /**
     * The payment card number.
     * Must be exactly 16 digits.
     */
    @NotBlank(message = "Card Number is mandatory")
    @Pattern(regexp = "\\d{16}", message = "Card Number must be 16 digits")
    private String cardNumber;

    /**
     * The expiry date of the payment card.
     * Must follow the MM/YY format.
     */
    @NotBlank(message = "Expiry Date is mandatory")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiry Date must be in MM/YY format")
    private String expiryDate;

    /**
     * The CVV code of the payment card.
     * Must be exactly 3 digits.
     * 
     * <strong>Note:</strong> Storing CVV is against PCI DSS compliance.
     * Consider handling CVV securely and avoid storing it if possible.
     */
    @NotBlank(message = "CVV is mandatory")
    @Pattern(regexp = "\\d{3}", message = "CVV must be 3 digits")
    private String cvv;

    // Constructors

    /**
     * Default constructor required for frameworks like Spring.
     */
    public PaymentCardVO() {}

    /**
     * Parameterized constructor for creating instances with all fields set.
     *
     * @param cardType   the type of the payment card (DEBIT or CREDIT)
     * @param cardNumber the payment card number
     * @param expiryDate the expiry date in MM/YY format
     * @param cvv        the 3-digit CVV code
     */
    public PaymentCardVO(CardType cardType, String cardNumber, String expiryDate, String cvv) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    // Getters and Setters

    /**
     * Retrieves the type of the payment card.
     *
     * @return the card type (DEBIT or CREDIT)
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * Sets the type of the payment card.
     *
     * @param cardType the card type to set (DEBIT or CREDIT)
     */
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    /**
     * Retrieves the payment card number.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the payment card number.
     *
     * @param cardNumber the card number to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Retrieves the expiry date of the payment card.
     *
     * @return the expiry date in MM/YY format
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the payment card.
     *
     * @param expiryDate the expiry date to set in MM/YY format
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Retrieves the CVV code of the payment card.
     *
     * @return the 3-digit CVV code
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the CVV code of the payment card.
     *
     * @param cvv the 3-digit CVV code to set
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    // Optional: Override toString to mask sensitive information

    @Override
    public String toString() {
        return "PaymentCardVO{" +
                "cardType=" + cardType +
                ", cardNumber='" + maskCardNumber(cardNumber) + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='***'" + // Mask CVV for security
                '}';
    }

    /**
     * Masks the payment card number for security purposes.
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