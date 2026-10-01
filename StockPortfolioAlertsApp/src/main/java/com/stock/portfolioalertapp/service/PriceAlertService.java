package com.stock.portfolioalertapp.service;

import com.stock.portfolioalertapp.entity.*;
import com.stock.portfolioalertapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PriceAlertService {
    private final PriceAlertRepository repo;
    private final UserRepository users;
    private final MarketService market;
    private final PriceAlertEventPublisher publisher;

    private User user(String email) {
        return users.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public PriceAlert create(String email, String symbol, PriceAlert.AlertType type, BigDecimal target) {
        if (symbol == null || symbol.isBlank()) throw new IllegalArgumentException("Stock symbol is required");
        if (type == null) throw new IllegalArgumentException("Alert type is required");
        if (target == null || target.signum() <= 0) throw new IllegalArgumentException("Target price must be positive");
        PriceAlert a = new PriceAlert();
        a.setUser(user(email));
        a.setStockSymbol(symbol.trim().toUpperCase());
        a.setAlertType(type);
        a.setTargetPrice(target);
        return repo.save(a);
    }

    public List<PriceAlert> mine(String email) {
        return repo.findByUserIdOrderByCreatedAtDesc(user(email).getId());
    }

    @Transactional
    public void delete(String email, UUID id) {
        PriceAlert a = repo.findById(id).orElseThrow(() -> new RuntimeException("Alert not found"));
        if (!a.getUser().getEmail().equalsIgnoreCase(email)) throw new RuntimeException("Access denied");
        repo.delete(a);
    }

    @Transactional
    public void checkAlerts() {
        for (PriceAlert a : repo.findByActiveTrueAndTriggeredFalse()) {
            try {
                BigDecimal price = market.getCurrentPrice(a.getStockSymbol());
                boolean hit = a.getAlertType() == PriceAlert.AlertType.BUY ? price.compareTo(a.getTargetPrice()) <= 0 : price.compareTo(a.getTargetPrice()) >= 0;
                if (hit) {
                    a.setTriggered(true);
                    a.setActive(false);
                    a.setTriggeredAt(LocalDateTime.now());
                    repo.save(a);
                    publisher.publish(a, price);
                }
            } catch (Exception ignored) {
            }
        }
    }
}
