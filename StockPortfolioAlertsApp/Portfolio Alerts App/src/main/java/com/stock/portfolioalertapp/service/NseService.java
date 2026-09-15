package com.stock.portfolioalertapp.service;

import org.springframework.stereotype.Service;

import com.github.pnpninja.nsetools.NSETools;
import com.github.pnpninja.nsetools.domain.StockQuote;

import java.math.BigDecimal;

@Service
public class NseService {

    private final NSETools nse;

    public NseService() {
        this.nse = new NSETools();
    }

    public BigDecimal getCurrentPrice(String stockSymbol) {

        try {

            StockQuote quote = nse.getQuote(
                    stockSymbol.toUpperCase()
            );

            if (quote == null) {
                throw new RuntimeException(
                        "Stock not found: " + stockSymbol
                );
            }

            return quote.getLastPrice();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to fetch NSE price for "
                            + stockSymbol,
                    e
            );
        }
    }
}