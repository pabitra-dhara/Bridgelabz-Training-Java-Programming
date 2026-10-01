package com.stock.portfolioalertapp.service;

import com.stock.portfolioalertapp.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TradeAlertConsumer {

    @RabbitListener(queues = RabbitMQConfig.ALERT_QUEUE)
    public void consume(String message) {
        System.out.println("RabbitMQ trade alert received: " + message);
    }
}
