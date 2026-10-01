package com.stock.portfolioalertapp.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TradeEventConsumer {

    @KafkaListener(topics = TradeEventPublisher.TOPIC, groupId = "portfolio-alerts")
    public void consume(String message) {
        System.out.println("Kafka trade event received: " + message);
    }
}
