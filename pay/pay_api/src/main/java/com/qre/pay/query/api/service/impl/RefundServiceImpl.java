package com.qre.pay.query.api.service.impl;

import com.qre.pay.dao.refund.RefundRepository;
import com.qre.pay.dto.refund.RefundRequest;
import com.qre.pay.dto.refund.RefundResponse;
import com.qre.pay.entity.refund.RefundTransaction;
import com.qre.pay.query.api.service.RefundService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.Refund;
import com.stripe.param.ChargeListParams;
import com.stripe.param.RefundCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class RefundServiceImpl implements RefundService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${stripe.secret.key}")
    private String secretKey;

    private final RefundRepository refundRepository;

    public RefundServiceImpl(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public RefundResponse refund(RefundRequest request) throws Exception {

        // TBD added business logic to capture refund data in database
        try {
            //Stripe.apiKey = secretKey;
            String serialNumber = request.getSerialNumber();
            String paymentIntentId = request.getPaymentIntentId();
            long refundAmount = request.getRefundAmount();

            //Find charge from payment intent
            ChargeListParams chargeParams = ChargeListParams.builder()
                    .setPaymentIntent(paymentIntentId)
                    .setLimit(1L)
                    .build();
            ChargeCollection charges = Charge.list(chargeParams);
            int chargeCount = charges.getData().size();
            if (chargeCount == 0) {
                logger.error("No charge found");
                return RefundResponse.builder()
                        .paymentIntentId(request.getPaymentIntentId())
                        .refundAmount(request.getRefundAmount())
                        .serialNumber(request.getSerialNumber())
                        .refundStatus(2) //0: in progress, 1: approved, 2: reject
                        .build();
            }
            Charge data = charges.getData().get(0);

            //amount integer, charge string, payment_intent string, reason string
            RefundCreateParams refundParams =
                    RefundCreateParams.builder()
                            .setCharge(data.getId())
                            .setAmount(refundAmount)
                            .setReason(RefundCreateParams.Reason.REQUESTED_BY_CUSTOMER)
                            .build();

            Refund refundResponse = Refund.create(refundParams);
            logger.info(String.valueOf(refundResponse));

            double refundAmt = ((double) refundResponse.getAmount()) / 100.0;
            logger.info(refundAmt + " " + refundResponse.getCurrency() + " refunded successfully");

            RefundTransaction refund = RefundTransaction.builder()
                    .serialNumber(serialNumber)
                    .paymentRefNo(paymentIntentId)
                    .amount(refundAmount)
                    .txnDatetime(new Date())
                    .currency(refundResponse.getCurrency())
                    .build();
            refundRepository.save(refund);

            return RefundResponse.builder()
                    .paymentIntentId(request.getPaymentIntentId())
                    .refundAmount(request.getRefundAmount())
                    .serialNumber(request.getSerialNumber())
                    .refundStatus(1) //0: in progress, 1: approved, 2: reject
                    .build();

        } catch (StripeException e) {
            String errorMsg = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            if (e.getCode() != null && e.getCode().equals("charge_already_refunded")) {
                errorMsg = "charge already refunded";
            }
            logger.error(errorMsg);
            return RefundResponse.builder()
                    .paymentIntentId(request.getPaymentIntentId())
                    .refundAmount(request.getRefundAmount())
                    .serialNumber(request.getSerialNumber())
                    .refundStatus(2) //0: in progress, 1: approved, 2: reject
                    .build();
        }

    }
}
