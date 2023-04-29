package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = reservationRepository.findById(reservationId).get();

       
        mode = mode.toUpperCase();

        PaymentMode paymentMode;
        if(mode.equals("CASH"))
            paymentMode = PaymentMode.CASH;
        else if(mode.equals("CARD"))
            paymentMode = PaymentMode.CARD;
        else if(mode.equals("UPI"))
            paymentMode = PaymentMode.UPI;
        else
            throw new Exception("Payment mode not detected");

        Payment payment = new Payment(true,paymentMode);
        payment.setReservation(reservation);

        reservation.setPayment(payment);

        reservationRepository.save(reservation);

        return payment;
    }
}
