/*
 * Copyright &copy MSI Global Pte Ltd (Singapore) 2022. All rights reserved.
 * The contents of this document are property of MSI Global Pte Ltd (Singapore).
 * No part of this work may be reproduced or transmitted in any form or by any means,
 * except as permitted by written license agreement with the MSI Global Pte Ltd
 * (Singapore).
 */
package com.qre.pay.query.api.route;

import com.qre.pay.query.api.route.processor.ExceptionHandlerProcessor;
import com.qre.pay.query.api.route.processor.RefundMessageProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RefundMessageRouter extends RouteBuilder {

    /**
     * MessageProcessor
     */
    private final RefundMessageProcessor refundMessageProcessor;

    /**
     * exceptionHandlerProcessor
     */
    private final ExceptionHandlerProcessor exceptionHandlerProcessor;

    /**
     *
     * @param refundMessageProcessor
     * @param exceptionHandlerProcessor
     */
    public RefundMessageRouter(RefundMessageProcessor refundMessageProcessor,
                               ExceptionHandlerProcessor exceptionHandlerProcessor) {
        this.refundMessageProcessor = refundMessageProcessor;
        this.exceptionHandlerProcessor = exceptionHandlerProcessor;
    }

    @Override
    public void configure() throws Exception {
        from("activemq:{{app.consumer.refund.request.queue}}")
                .autoStartup(true)
                .routeId("REFUND_MESSAGE_ROUTE")
                    .log(LoggingLevel.INFO, "Received message from ActiveMQ : ${body}")
                .doTry()
                    .process(refundMessageProcessor)
                    .log(LoggingLevel.INFO, "Process completed")
                    .log(LoggingLevel.INFO, "Send message to ActiveMQ : ${body}")
                    .to("activemq:{{app.procedure.refund.approve.queue}}")
                .doCatch(Exception.class)
                    .log(LoggingLevel.ERROR, "Message failed to process" + exceptionMessage())
                    .process(exceptionHandlerProcessor)
                .end();
    }

}
