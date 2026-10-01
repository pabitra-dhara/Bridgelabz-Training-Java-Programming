package com.stock.portfolioalertapp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String TRADE_QUEUE = "stock.alert.queue";
    public static final String ALERT_QUEUE = TRADE_QUEUE;
    public static final String EMAIL_QUEUE = "stock.email.queue";
    public static final String REGISTRATION_EMAIL_QUEUE = "registration.email.queue";

    @Bean
    public Queue tradeAlertQueue() {
        return new Queue(TRADE_QUEUE, true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public Queue registrationEmailQueue() {
        return new Queue(REGISTRATION_EMAIL_QUEUE, true);
    }
}
