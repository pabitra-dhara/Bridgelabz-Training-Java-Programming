package com.stock.portfolioalertapp.dto;

import java.math.BigDecimal;

public class BuyStockRequest {

    private String stockSymbol;
    private BigDecimal quantity;

    public BuyStockRequest() {}

    public String getStockSymbol() { return stockSymbol; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
}
