package com.stock.portfolioalertapp.dto;
import java.math.BigDecimal; import java.util.UUID;
public class PortfolioStockRequest {private UUID portfolioId; private String stockSymbol; private BigDecimal quantity; public PortfolioStockRequest(){}
 public UUID getPortfolioId(){return portfolioId;} public void setPortfolioId(UUID v){portfolioId=v;} public String getStockSymbol(){return stockSymbol;} public void setStockSymbol(String v){stockSymbol=v;} public BigDecimal getQuantity(){return quantity;} public void setQuantity(BigDecimal v){quantity=v;}}
