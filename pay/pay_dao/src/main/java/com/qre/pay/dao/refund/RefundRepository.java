package com.qre.pay.dao.refund;

import com.qre.pay.entity.refund.RefundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefundRepository extends JpaRepository<RefundTransaction, UUID> {
}
