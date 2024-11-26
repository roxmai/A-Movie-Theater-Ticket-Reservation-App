package com.example.acmeplex.paymentsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.acmeplex.moviesystem.dto.TicketBookingDTO;
import com.example.acmeplex.paymentsystem.dto.TicketPaymentDTO;
import com.example.acmeplex.paymentsystem.service.PaymentService;

public class PaymentController {
    
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/ticketpayment")
    public ResponseEntity<String> ticketPayment(@RequestBody TicketPaymentDTO ticketPaymentDTO) {
        return ResponseEntity.ok(paymentService.processMoviePayment(ticketPaymentDTO));
    }

    @PutMapping("/membershippayment/{email}/{method}")
    public ResponseEntity<String> membershipPayment(@PathVariable String email, @PathVariable String method) {
        return ResponseEntity.ok(paymentService.processMembershipPayment(email, method));
    }

    @PostMapping("/totalprice")
    public ResponseEntity<Double> totalTicketPrice(@RequestBody TicketBookingDTO ticketBookingDTO) {
        return ResponseEntity.ok(paymentService.priceCalculation(ticketBookingDTO.getIds()));
    }

    @PutMapping("/issuerefund/{ticketnumber}")
    public ResponseEntity<String> issueRefund(@PathVariable String ticketnumber) {
        return ResponseEntity.ok(paymentService.issueCredit(ticketnumber));
    }




}
