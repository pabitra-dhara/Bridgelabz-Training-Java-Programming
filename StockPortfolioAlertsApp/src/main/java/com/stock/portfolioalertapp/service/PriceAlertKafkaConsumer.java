package com.stock.portfolioalertapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.portfolioalertapp.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceAlertKafkaConsumer {
    private final RabbitTemplate rabbit;
    private final ObjectMapper mapper;

    @KafkaListener(topics = PriceAlertEventPublisher.TOPIC, groupId = "price-alert-email")
    public void consume(String message) {
        try {
            JsonNode n = mapper.readTree(message);
            rabbit.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, message);
        } catch (Exception e) {
            System.err.println("Price alert Kafka consumer failed: " + e.getMessage());
        }
    }
}
