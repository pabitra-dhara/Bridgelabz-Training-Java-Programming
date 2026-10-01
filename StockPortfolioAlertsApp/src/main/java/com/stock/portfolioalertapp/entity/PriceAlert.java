package com.stock.portfolioalertapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "price_alerts")
public class PriceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String stockSymbol;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType alertType;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal targetPrice;
    @Column(nullable = false)
    private boolean active = true;
    @Column(nullable = false)
    private boolean triggered = false;
    private LocalDateTime createdAt;
    private LocalDateTime triggeredAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID v) {
        id = v;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User v) {
        user = v;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String v) {
        stockSymbol = v;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType v) {
        alertType = v;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal v) {
        targetPrice = v;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean v) {
        active = v;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean v) {
        triggered = v;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getTriggeredAt() {
        return triggeredAt;
    }

    public void setTriggeredAt(LocalDateTime v) {
        triggeredAt = v;
    }

    public enum AlertType {BUY, SELL}
}
