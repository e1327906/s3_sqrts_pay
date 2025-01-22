package com.qre.pay.query.api.route.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qre.pay.dto.refund.RefundRequest;
import com.qre.pay.dto.refund.RefundResponse;
import com.qre.pay.query.api.service.RefundService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RefundMessageProcessor implements Processor {

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(RefundMessageProcessor.class);

    /**
     * objectMapper
     */
    private final ObjectMapper objectMapper;

    /**
     * refundService
     */
    private final RefundService refundService;


    public RefundMessageProcessor(ObjectMapper objectMapper,
                                  RefundService refundService) {
        this.objectMapper = objectMapper;
        this.refundService = refundService;
    }

    /**
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        try {
            String strMessage = exchange.getIn().getBody(String.class);
            logger.info("Processing message: {}", strMessage);

            RefundRequest request = objectMapper.readValue(strMessage, RefundRequest.class);
            // TBD added business logic to capture refund data in database
            RefundResponse response = refundService.refund(request);
            exchange.getIn().setBody(objectMapper.writeValueAsString(response));

            logger.info("Refund response message: {}", strMessage);

        } catch (Exception ex) {
            logger.error("Error processing message", ex);
            throw new Exception("Failed to process message", ex);
        }
    }

}
