package com.stock.portfolioalertapp.dto;

import java.math.BigDecimal;
import java.util.List;

public class PortfolioSummaryResponse {

    private BigDecimal investedAmount;
    private BigDecimal currentValue;
    private BigDecimal unrealizedProfitLoss;
    private BigDecimal realizedProfitLoss;
    private BigDecimal totalProfitLoss;
    private List<HoldingSummary> holdings;

    public PortfolioSummaryResponse() {}

    public PortfolioSummaryResponse(BigDecimal investedAmount,
                                    BigDecimal currentValue,
                                    BigDecimal unrealizedProfitLoss,
                                    BigDecimal realizedProfitLoss,
                                    BigDecimal totalProfitLoss,
                                    List<HoldingSummary> holdings) {
        this.investedAmount = investedAmount;
        this.currentValue = currentValue;
        this.unrealizedProfitLoss = unrealizedProfitLoss;
        this.realizedProfitLoss = realizedProfitLoss;
        this.totalProfitLoss = totalProfitLoss;
        this.holdings = holdings;
    }

    public BigDecimal getInvestedAmount() { return investedAmount; }
    public BigDecimal getCurrentValue() { return currentValue; }
    public BigDecimal getUnrealizedProfitLoss() { return unrealizedProfitLoss; }
    public BigDecimal getRealizedProfitLoss() { return realizedProfitLoss; }
    public BigDecimal getTotalProfitLoss() { return totalProfitLoss; }
    public List<HoldingSummary> getHoldings() { return holdings; }

    public static class HoldingSummary {
        private String stockSymbol;
        private BigDecimal quantity;
        private BigDecimal avgBuyPrice;
        private BigDecimal currentPrice;
        private BigDecimal investedAmount;
        private BigDecimal currentValue;
        private BigDecimal profitLoss;

        public HoldingSummary(String stockSymbol, BigDecimal quantity,
                              BigDecimal avgBuyPrice, BigDecimal currentPrice,
                              BigDecimal investedAmount, BigDecimal currentValue,
                              BigDecimal profitLoss) {
            this.stockSymbol = stockSymbol;
            this.quantity = quantity;
            this.avgBuyPrice = avgBuyPrice;
            this.currentPrice = currentPrice;
            this.investedAmount = investedAmount;
            this.currentValue = currentValue;
            this.profitLoss = profitLoss;
        }

        public String getStockSymbol() { return stockSymbol; }
        public BigDecimal getQuantity() { return quantity; }
        public BigDecimal getAvgBuyPrice() { return avgBuyPrice; }
        public BigDecimal getCurrentPrice() { return currentPrice; }
        public BigDecimal getInvestedAmount() { return investedAmount; }
        public BigDecimal getCurrentValue() { return currentValue; }
        public BigDecimal getProfitLoss() { return profitLoss; }
    }
}
