package com.stock.portfolioalertapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.portfolioalertapp.entity.PriceAlert;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceAlertEventPublisher {
    public static final String TOPIC = "price-alerts";
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;

    public void publish(PriceAlert a, BigDecimal currentPrice) {
        try {
            String json = mapper.writeValueAsString(Map.of("alertId", a.getId().toString(), "email", a.getUser().getEmail(), "name", a.getUser().getName(), "stockSymbol", a.getStockSymbol(), "alertType", a.getAlertType().name(), "targetPrice", a.getTargetPrice(), "currentPrice", currentPrice));
            kafka.send(TOPIC, a.getStockSymbol(), json);
        } catch (Exception e) {
            System.err.println("Price alert Kafka publish failed: " + e.getMessage());
        }
    }
}
