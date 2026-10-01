package com.stock.portfolioalertapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TradeResponse {

    private String transactionType;
    private String stockSymbol;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private BigDecimal realizedProfitLoss;
    private LocalDateTime transactionAt;

    public TradeResponse() {}

    public TradeResponse(String transactionType, String stockSymbol,
                         BigDecimal quantity, BigDecimal price,
                         BigDecimal totalAmount,
                         BigDecimal realizedProfitLoss,
                         LocalDateTime transactionAt) {
        this.transactionType = transactionType;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
        this.realizedProfitLoss = realizedProfitLoss;
        this.transactionAt = transactionAt;
    }

    public String getTransactionType() { return transactionType; }
    public String getStockSymbol() { return stockSymbol; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BigDecimal getRealizedProfitLoss() { return realizedProfitLoss; }
    public LocalDateTime getTransactionAt() { return transactionAt; }
}
