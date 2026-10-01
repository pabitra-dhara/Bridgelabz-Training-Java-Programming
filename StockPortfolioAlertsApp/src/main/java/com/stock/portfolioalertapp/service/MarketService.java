package com.stock.portfolioalertapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stock.portfolioalertapp.client.IndianApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final IndianApiClient indianApiClient;

    public String getQuote(String symbol) {
        return indianApiClient.getQuote(symbol.trim().toUpperCase());
    }

    public String getTrendingStocks() {
        return indianApiClient.getTrendingStocks();
    }

    public String searchStock(String query) {
        if (query == null || query.isBlank()) throw new IllegalArgumentException("Search query is required");
        return indianApiClient.getQuote(query.trim().toUpperCase());
    }

    public BigDecimal getCurrentPrice(String symbol) {
        String normalized = symbol.trim().toUpperCase();
        JsonNode quote = indianApiClient.getQuoteNode(normalized);

        JsonNode currentPrice = quote.path("currentPrice");

        BigDecimal price = decimal(currentPrice.path("NSE"));
        if (price == null) {
            price = decimal(currentPrice.path("BSE"));
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException(
                    "Current NSE/BSE price not available for " + normalized);
        }

        return price;
    }

    public String getCompanyName(String symbol) {
        JsonNode quote = indianApiClient.getQuoteNode(symbol.trim().toUpperCase());
        String name = quote.path("companyName").asText("");
        return name.isBlank() ? symbol.trim().toUpperCase() : name;
    }

    private BigDecimal decimal(JsonNode node) {
        if (node == null || node.isMissingNode() || node.isNull()) {
            return null;
        }

        if (node.isNumber()) {
            return node.decimalValue();
        }

        String value = node.asText("");
        if (value.isBlank() || "-".equals(value)) {
            return null;
        }

        try {
            return new BigDecimal(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
