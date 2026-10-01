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
public class RegistrationEmailConsumer {
    private final ObjectMapper mapper;
    private final EmailService email;

    @RabbitListener(queues = RabbitMQConfig.REGISTRATION_EMAIL_QUEUE)
    public void consume(String message) {
        try {
            JsonNode n = mapper.readTree(message);
            email.sendRegistration(n.path("email").asText(), n.path("name").asText());
        } catch (Exception e) {
            System.err.println("Registration email failed: " + e.getMessage());
        }
    }
}
