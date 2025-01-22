package com.qre.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.qre")
public class PaymentApiApplication {

    private Logger logger = LoggerFactory.getLogger(PaymentApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PaymentApiApplication.class, args);
    }

}
