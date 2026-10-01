package com.stock.portfolioalertapp.service;

import com.fasterxml.jackson.databind.*;
import com.stock.portfolioalertapp.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.mail.enabled", havingValue = "true")
public class PriceAlertEmailConsumer {
    private final ObjectMapper mapper;
    private final EmailService email;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consume(String message) {
        try {
            JsonNode n = mapper.readTree(message);
            email.sendPriceAlert(n.path("email").asText(), n.path("name").asText(), n.path("stockSymbol").asText(), n.path("alertType").asText(), n.path("targetPrice").asText(), n.path("currentPrice").asText());
        } catch (Exception e) {
            System.err.println("Price alert email failed: " + e.getMessage());
        }
    }
}
