package com.example.acmeplex.paymentsystem.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.acmeplex.paymentsystem.repository.CreditRecordRepository;
import com.example.acmeplex.paymentsystem.repository.PaymentRepository;
import com.example.acmeplex.usersystem.service.RegisteredUserService;
import com.example.acmeplex.moviesystem.repository.TicketRepository;
import com.example.acmeplex.paymentsystem.entity.CreditRecord;
import com.example.acmeplex.paymentsystem.entity.Payment;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CreditRecordRepository creditRecordRepository;
    private final TicketRepository ticketRepository;
    private final RegisteredUserService registeredUserService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, CreditRecordRepository creditRecordRepository, TicketRepository ticketRepository, RegisteredUserService registeredUserService) {
        this.paymentRepository = paymentRepository;
        this.creditRecordRepository = creditRecordRepository;
        this.ticketRepository = ticketRepository;
        this.registeredUserService = registeredUserService;
    }

    @Transactional
    public double priceCalculation(List<Integer> showtimeSeats){
        double totalPayment = 0;
        for(Integer showtimeSeatId : showtimeSeats) {
            double price = ticketRepository.getTicketPrice(showtimeSeatId);
            totalPayment += price;
        }
        return totalPayment;
    }

    @Transactional
    public String processMoviePayment(List<Integer> showtimeSeats, String email, String method){
        try {
            double totalPayment = priceCalculation(showtimeSeats);

            double creditUsed=0;
            List<CreditRecord> creditRecords = sortCreditRecords(email);
            for(int i=0; i<creditRecords.size(); i++) {
                CreditRecord creditRecord = creditRecords.get(i);
                double creditPoints = creditRecord.getCreditPoints();
                double usedPoints = creditRecord.getUsedPoints();
                double creditAvailable = creditPoints - usedPoints;
                if (creditAvailable > 0) {
                    if (creditAvailable >= (totalPayment - creditUsed)) {                        
                        creditRecord.setUsedPoints(usedPoints + totalPayment - creditUsed);
                        creditRecordRepository.updateUsedPoints(creditRecord.getId(), creditRecord.getUsedPoints());
                        creditUsed += creditAvailable;
                    } else {                        
                        creditRecord.setUsedPoints(creditPoints);
                        creditRecordRepository.updateUsedPoints(creditRecord.getId(), creditRecord.getUsedPoints());
                        creditUsed += creditAvailable;
                    }
                }
                if (totalPayment <=creditUsed)  {
                    break;
                }
            }

            double remainingPayment = totalPayment - creditUsed;

            int newPaymentId = paymentRepository.getLastPaymentId() + 1;
            Payment payment = new Payment(email, method, newPaymentId, totalPayment, "ticket");
            paymentRepository.addPayment(payment);

            for(Integer showtimeSeatId : showtimeSeats) {
                String ticketNumber = ticketRepository.getTicketNumber(showtimeSeatId);
                paymentRepository.addPaymentTicket(payment, ticketNumber , "paid");
            }

            return "Success:"+String.valueOf(totalPayment)+" processed successfully."+String.valueOf(creditUsed)+" credit points used."+String.valueOf(totalPayment-creditUsed)+" remaining payment charged to " + method+ "card. ";        
        } catch (RuntimeException exception) {
            return "error: " + exception.getMessage();
        }
    }

    @Transactional
    public String processMembershipPayment(String email, String method){
        try {
            double totalPayment = 20;

            double creditUsed=0;
            List<CreditRecord> creditRecords = sortCreditRecords(email);
            for(int i=0; i<creditRecords.size(); i++) {
                CreditRecord creditRecord = creditRecords.get(i);
                double creditPoints = creditRecord.getCreditPoints();
                double usedPoints = creditRecord.getUsedPoints();
                double creditAvailable = creditPoints - usedPoints;
                if (creditAvailable > 0) {
                    if (creditAvailable >= (totalPayment - creditUsed)) {                        
                        creditRecord.setUsedPoints(usedPoints + totalPayment - creditUsed);
                        creditRecordRepository.updateUsedPoints(creditRecord.getId(), creditRecord.getUsedPoints());
                        creditUsed += creditAvailable;
                    } else {                        
                        creditRecord.setUsedPoints(creditPoints);
                        creditRecordRepository.updateUsedPoints(creditRecord.getId(), creditRecord.getUsedPoints());
                        creditUsed += creditAvailable;
                    }
                }
                if (totalPayment <=creditUsed)  {
                    break;
                }
            }

            double remainingPayment = totalPayment - creditUsed;
            
            int newPaymentId = paymentRepository.getLastPaymentId() + 1;
            Payment payment = new Payment(email, method, newPaymentId, totalPayment, "ticket");
            paymentRepository.addPayment(payment);

            return "Success:"+String.valueOf(totalPayment)+" processed successfully."+String.valueOf(creditUsed)+" credit points used."+String.valueOf(totalPayment-creditUsed)+" remaining payment charged to " + method+ "card. "; 
        } catch (RuntimeException exception) {
            return "error: " + exception.getMessage();
        }
    }

    @Transactional
    public String issueCredit(String ticketNumber){
        try {
            String email = ticketRepository.getEmailByTicketNumber(ticketNumber);

            double ticketPrice = ticketRepository.getTicketPrice(ticketNumber);
            double creditPoints;
            if (registeredUserService.validRegisteredUser(email)){
                creditPoints = ticketPrice;
            }
            else{
                creditPoints = ticketPrice*0.85;
            }

            int creditId= creditRecordRepository.getLastCreditRecordId() + 1;

            Date today= new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.YEAR, 1);
            Date expirationDate = cal.getTime();

            CreditRecord creditRecord = new CreditRecord(creditId, email, creditPoints, 0, expirationDate);
            creditRecordRepository.addCreditRecord(creditRecord);

            paymentRepository.updatePaymentStatus(ticketNumber, "credited");
            
            return "Success! Credit points issued: "+String.valueOf(creditPoints);
        } catch (RuntimeException exception) {
            return "error: " + exception.getMessage();
        }
    }

    private List<CreditRecord> sortCreditRecords(String email) {
        
        List<CreditRecord> creditRecords = creditRecordRepository.getValidCreditRecordByEmail(email);
        Collections.sort(creditRecords, new Comparator<CreditRecord>() {
            @Override
            public int compare(CreditRecord cr1, CreditRecord cr2) {
                return cr1.getExpirationDate().compareTo(cr2.getExpirationDate());
            }
        });
        return creditRecords;
    }

    
}
