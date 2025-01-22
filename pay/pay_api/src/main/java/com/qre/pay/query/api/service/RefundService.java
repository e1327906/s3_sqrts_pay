package com.qre.pay.query.api.service;

import com.qre.pay.dto.refund.RefundRequest;
import com.qre.pay.dto.refund.RefundResponse;

public interface RefundService {
    RefundResponse refund(RefundRequest request) throws Exception;
}
