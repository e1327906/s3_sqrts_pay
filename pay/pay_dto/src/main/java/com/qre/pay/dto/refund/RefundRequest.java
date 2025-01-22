package com.qre.pay.dto.refund;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.qre.pay.dto.base.JsonFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundRequest {

    @JsonProperty(JsonFieldName.SERIAL_NUMBER)
    private String serialNumber;

    @JsonProperty(JsonFieldName.PAYMENT_INTENT_ID)
    private String paymentIntentId;

    @JsonProperty(JsonFieldName.REFUND_AMOUNT)
    private long refundAmount;

}
