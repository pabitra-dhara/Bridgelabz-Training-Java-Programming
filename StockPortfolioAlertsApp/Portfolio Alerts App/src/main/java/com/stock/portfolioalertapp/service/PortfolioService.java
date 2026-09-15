package com.stock.portfolioalertapp.service;

import com.stock.portfolioalertapp.dto.BuyStockRequest;
import com.stock.portfolioalertapp.entity.Holding;
import com.stock.portfolioalertapp.entity.Portfolio;
import com.stock.portfolioalertapp.entity.User;
import com.stock.portfolioalertapp.repository.HoldingRepository;
import com.stock.portfolioalertapp.repository.PortfolioRepository;
import com.stock.portfolioalertapp.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class PortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;
    private final NseService nseService;

    public PortfolioService(
            UserRepository userRepository,
            PortfolioRepository portfolioRepository,
            HoldingRepository holdingRepository,
            NseService nseService
    ) {
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.holdingRepository = holdingRepository;
        this.nseService = nseService;
    }

    public Holding buyStock(
            UUID portfolioId,
            String email,
            BuyStockRequest request
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Portfolio portfolio =
                portfolioRepository
                        .findByIdAndUser(
                                portfolioId,
                                user
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Portfolio not found"
                                )
                        );

        String stockSymbol =
                request.getStockSymbol()
                        .trim()
                        .toUpperCase();

        BigDecimal quantity =
                request.getQuantity();

        // Get current price from NSE
        BigDecimal buyPrice =
                nseService.getCurrentPrice(
                        stockSymbol
                );

        Holding holding =
                holdingRepository
                        .findByPortfolioAndStockSymbol(
                                portfolio,
                                stockSymbol
                        )
                        .orElse(null);

        if (holding == null) {

            holding = new Holding();

            holding.setPortfolio(portfolio);
            holding.setStockSymbol(stockSymbol);
            holding.setQuantity(quantity);
            holding.setAvgBuyPrice(buyPrice);

        } else {

            BigDecimal oldQuantity =
                    holding.getQuantity();

            BigDecimal oldAveragePrice =
                    holding.getAvgBuyPrice();

            BigDecimal oldTotalValue =
                    oldQuantity.multiply(
                            oldAveragePrice
                    );

            BigDecimal newTotalValue =
                    quantity.multiply(
                            buyPrice
                    );

            BigDecimal totalQuantity =
                    oldQuantity.add(quantity);

            BigDecimal newAveragePrice =
                    oldTotalValue
                            .add(newTotalValue)
                            .divide(
                                    totalQuantity,
                                    6,
                                    RoundingMode.HALF_UP
                            );

            holding.setQuantity(totalQuantity);

            holding.setAvgBuyPrice(
                    newAveragePrice
            );
        }

        return holdingRepository.save(holding);
    }
}