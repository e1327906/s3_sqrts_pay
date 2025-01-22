package com.qre.pay.dao.payment;

import com.qre.pay.entity.payment.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTransaction, UUID> {

    Optional<PaymentTransaction> findByPaymentRefNo(String paymentRefNo);
}
