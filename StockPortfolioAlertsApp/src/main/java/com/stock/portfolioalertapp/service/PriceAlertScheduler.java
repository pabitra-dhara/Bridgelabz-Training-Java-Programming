package com.stock.portfolioalertapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceAlertScheduler {
    private final PriceAlertService service;

    @Scheduled(fixedDelayString = "${alerts.check-ms:30000}")
    public void check() {
        service.checkAlerts();
    }
}
