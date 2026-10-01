package com.stock.portfolioalertapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "portfolio_stocks", uniqueConstraints = @UniqueConstraint(name = "uk_portfolio_holding", columnNames = {"portfolio_id", "holding_id"}))
public class PortfolioStock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "holding_id", nullable = false)
    private Holding holding;
    @Column(nullable = false)
    private String stockSymbol;
    private String companyName;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal avgBuyPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio v) {
        portfolio = v;
    }

    public Holding getHolding() {
        return holding;
    }

    public void setHolding(Holding v) {
        holding = v;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String v) {
        stockSymbol = v;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String v) {
        companyName = v;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal v) {
        quantity = v;
    }

    public BigDecimal getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(BigDecimal v) {
        avgBuyPrice = v;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
