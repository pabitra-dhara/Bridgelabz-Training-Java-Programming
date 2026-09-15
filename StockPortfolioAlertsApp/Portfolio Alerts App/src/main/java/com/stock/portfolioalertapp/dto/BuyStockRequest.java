package com.stock.portfolioalertapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class BuyStockRequest {

    @NotBlank(message = "Stock symbol is required")
    private String stockSymbol;

    @NotNull(message = "Quantity is required")
    @DecimalMin(
            value = "0.000001",
            message = "Quantity must be greater than 0"
    )
    private BigDecimal quantity;

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}