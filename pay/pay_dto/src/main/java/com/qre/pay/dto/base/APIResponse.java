package com.qre.pay.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse {
    @JsonProperty("ResponseCode")
    private String responseCode;

    @JsonProperty("ResponseMsg")
    private String responseMsg;

    @JsonProperty("ResponseData")
    private Object responseData;

}

