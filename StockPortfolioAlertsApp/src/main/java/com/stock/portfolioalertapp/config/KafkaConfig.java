package com.stock.portfolioalertapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Bean
    public NewTopic tradeTopic() {
        return new NewTopic("portfolio-trades", 3, (short) 1);
    }

    @Bean
    public NewTopic priceAlertTopic() {
        return new NewTopic("price-alerts", 3, (short) 1);
    }
}
