package com.qre.pay.query.api.service;


import com.qre.pay.entity.payment.PaymentTransaction;

import java.util.Optional;

public interface PaymentService {

    void savePayment(PaymentTransaction paymentTransaction) throws Exception;

    Optional findByPaymentRefNo(String paymentRefNo);
}