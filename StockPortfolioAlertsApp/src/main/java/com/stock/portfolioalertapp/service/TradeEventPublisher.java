package com.stock.portfolioalertapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.portfolioalertapp.config.RabbitMQConfig;
import com.stock.portfolioalertapp.entity.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
public class TradeEventPublisher {

    public static final String TOPIC = "portfolio-trades";

    private final KafkaTemplate kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public TradeEventPublisher(KafkaTemplate kafkaTemplate,
                                RabbitTemplate rabbitTemplate,
                                StringRedisTemplate redisTemplate,
                                ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Transaction transaction) {
        Map<String, Object> event = Map.of(
                "transactionId", transaction.getId().toString(),
                "userId", transaction.getUser().getId().toString(),
                "stockSymbol", transaction.getStockSymbol(),
                "transactionType", transaction.getTransactionType(),
                "quantity", transaction.getQuantity(),
                "price", transaction.getPrice(),
                "totalAmount", transaction.getTotalAmount()
        );

        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, transaction.getStockSymbol(), json);
        } catch (Exception e) {
            System.err.println("Kafka publish failed: " + e.getMessage());
        }

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ALERT_QUEUE,
                    objectMapper.writeValueAsString(event));
        } catch (Exception e) {
            System.err.println("RabbitMQ publish failed: " + e.getMessage());
        }

        try {
            String key = "trade:" + transaction.getId();
            redisTemplate.opsForValue().set(
                    key,
                    objectMapper.writeValueAsString(event),
                    Duration.ofHours(24));
        } catch (Exception e) {
            System.err.println("Redis write failed: " + e.getMessage());
        }
    }
}
