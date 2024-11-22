package com.example.acmeplex.paymentsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.acmeplex.paymentsystem.repository.CreditRecordRepository;
import com.example.acmeplex.paymentsystem.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CreditRecordRepository creditRecordRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, CreditRecordRepository creditRecordRepository) {
        this.paymentRepository = paymentRepository;
        this.creditRecordRepository = creditRecordRepository;
    }

    // public Boolean processPayment() {
    //     // Process payment logic
    //     paymentStatus = true;
    //     //add send email receipt logic

    //     return paymentStatus;
    // }

    // public Boolean issueRefund() {
    //     // Refund payment logic
    //     paymentStatus = false;
    //     // add send email refund logic

    //     return true;
    // }
    
}
