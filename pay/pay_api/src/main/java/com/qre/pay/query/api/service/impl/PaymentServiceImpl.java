package com.qre.pay.query.api.service.impl;

import com.qre.pay.dao.payment.PaymentRepository;
import com.qre.pay.dao.refund.RefundRepository;
import com.qre.pay.dto.refund.RefundRequest;
import com.qre.pay.dto.refund.RefundResponse;
import com.qre.pay.entity.payment.PaymentTransaction;
import com.qre.pay.entity.refund.RefundTransaction;
import com.qre.pay.query.api.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.ChargeListParams;
import com.stripe.param.RefundCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    @Override
    public void savePayment(PaymentTransaction paymentTransaction) throws Exception {

            paymentRepository.save(paymentTransaction);

    }

    @Override
    public Optional<PaymentTransaction> findByPaymentRefNo(String paymentRefNo) {
        return paymentRepository.findByPaymentRefNo(paymentRefNo);
    }
}
