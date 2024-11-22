package com.example.acmeplex.paymentsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.acmeplex.moviesystem.entity.Ticket;
import com.example.acmeplex.paymentsystem.repository.CreditRecordRepository;
import com.example.acmeplex.paymentsystem.repository.PaymentRepository;
import com.example.acmeplex.moviesystem.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CreditRecordRepository creditRecordRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, CreditRecordRepository creditRecordRepository, TicketRepository ticketRepository) {
        this.paymentRepository = paymentRepository;
        this.creditRecordRepository = creditRecordRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public String processMoviePayment(List<Integer> showtimeSeats, String email){
        try {
            
        } catch (RuntimeException exception) {
            return "error: " + exception.getMessage();
        }
    }

    @Transactional
    public String processMembershipPayment(String email){
        try {
            
        } catch (RuntimeException exception) {
            return "error: " + exception.getMessage();
        }
    }

    @Transactional
    public String issueCredit(String email){
        try {
            
        } catch (RuntimeException exception) {
            return "error: " + exception.getMessage();
        }
    }
    
}
