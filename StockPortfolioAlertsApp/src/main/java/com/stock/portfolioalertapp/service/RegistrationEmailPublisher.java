package com.stock.portfolioalertapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.portfolioalertapp.config.RabbitMQConfig;
import com.stock.portfolioalertapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegistrationEmailPublisher {
    private final RabbitTemplate rabbit;
    private final ObjectMapper mapper;

    public void publish(User u) {
        try {
            rabbit.convertAndSend(RabbitMQConfig.REGISTRATION_EMAIL_QUEUE, mapper.writeValueAsString(Map.of("email", u.getEmail(), "name", u.getName())));
        } catch (Exception e) {
            System.err.println("Registration email queue failed: " + e.getMessage());
        }
    }
}
