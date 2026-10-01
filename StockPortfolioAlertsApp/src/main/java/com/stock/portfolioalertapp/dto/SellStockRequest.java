package com.stock.portfolioalertapp.dto;

import java.math.BigDecimal;

public class SellStockRequest {

    private String stockSymbol;
    private BigDecimal quantity;

    public SellStockRequest() {}

    public String getStockSymbol() { return stockSymbol; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
}
