package com.stock.portfolioalertapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "watchlist_stocks", uniqueConstraints = @UniqueConstraint(name = "uk_watchlist_stock", columnNames = {"watchlist_id", "stock_symbol"}))
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "watchlist_id", nullable = false)
    private WatchlistCollection watchlist;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "stock_symbol", nullable = false)
    private String stockName;
    private String isin;
    private String companyName;
    private String sector;
    @Column(nullable = false)
    private String currency = "INR";
    @Column(precision = 19, scale = 4)
    private BigDecimal week52High;
    @Column(precision = 19, scale = 4)
    private BigDecimal week52Low;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (currency == null || currency.isBlank()) currency = "INR";
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

    public void setId(UUID v) {
        id = v;
    }

    public WatchlistCollection getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(WatchlistCollection v) {
        watchlist = v;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User v) {
        user = v;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String v) {
        stockName = v;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String v) {
        isin = v;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String v) {
        companyName = v;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String v) {
        sector = v;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String v) {
        currency = v;
    }

    public BigDecimal getWeek52High() {
        return week52High;
    }

    public void setWeek52High(BigDecimal v) {
        week52High = v;
    }

    public BigDecimal getWeek52Low() {
        return week52Low;
    }

    public void setWeek52Low(BigDecimal v) {
        week52Low = v;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
