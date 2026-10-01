package com.stock.portfolioalertapp.service;

import com.stock.portfolioalertapp.entity.*;
import com.stock.portfolioalertapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.*;
import java.util.*;

@Service
public class PortfolioStockService {
    private final PortfolioStockRepository stockRepo;
    private final PortfolioRepository portfolioRepo;
    private final HoldingRepository holdingRepo;
    private final UserRepository userRepo;
    private final TransactionRepository transactionRepo;
    private final TradeEventPublisher publisher;
    private final MarketService market;

    public PortfolioStockService(PortfolioStockRepository s, PortfolioRepository p, HoldingRepository h, UserRepository u, TransactionRepository t, TradeEventPublisher pub, MarketService m) {
        stockRepo = s;
        portfolioRepo = p;
        holdingRepo = h;
        userRepo = u;
        transactionRepo = t;
        publisher = pub;
        market = m;
    }

    private User user(String e) {
        return userRepo.findByEmail(e).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Portfolio portfolio(String e, UUID id) {
        return portfolioRepo.findByIdAndUserId(id, user(e).getId()).orElseThrow(() -> new RuntimeException("Portfolio not found or access denied"));
    }

    private String symbol(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("Stock symbol is required");
        return s.trim().toUpperCase();
    }

    @Transactional
    public PortfolioStock addHoldingToPortfolio(String email, UUID portfolioId, String stockSymbol) {
        Portfolio p = portfolio(email, portfolioId);
        User u = p.getUser();
        String s = symbol(stockSymbol);
        Holding h = holdingRepo.findByUserIdAndStockSymbol(u.getId(), s).orElseThrow(() -> new IllegalArgumentException("Buy the stock first. Holding not found for " + s));
        PortfolioStock ps = stockRepo.findByPortfolioIdAndHoldingId(portfolioId, h.getId()).orElseGet(PortfolioStock::new);
        ps.setPortfolio(p);
        ps.setHolding(h);
        ps.setStockSymbol(s);
        ps.setCompanyName(h.getCompanyName());
        ps.setQuantity(h.getQuantity());
        ps.setAvgBuyPrice(h.getAvgBuyPrice());
        return stockRepo.save(ps);
    }

    public List<PortfolioStock> getPortfolioStocks(String email, UUID portfolioId) {
        portfolio(email, portfolioId);
        return stockRepo.findByPortfolioId(portfolioId);
    }

    @Transactional
    public Transaction sellFromPortfolio(String email, UUID portfolioId, String stockSymbol, BigDecimal quantity) {
        if (quantity == null || quantity.signum() <= 0) throw new IllegalArgumentException("Quantity must be positive");
        Portfolio p = portfolio(email, portfolioId);
        String s = symbol(stockSymbol);
        Holding h = holdingRepo.findByUserIdAndStockSymbol(p.getUser().getId(), s).orElseThrow(() -> new IllegalArgumentException("Holding not found"));
        if (quantity.compareTo(h.getQuantity()) > 0)
            throw new IllegalArgumentException("Insufficient holding quantity");
        BigDecimal price = market.getCurrentPrice(s);
        BigDecimal pnl = price.subtract(h.getAvgBuyPrice()).multiply(quantity).setScale(4, RoundingMode.HALF_UP);
        BigDecimal remaining = h.getQuantity().subtract(quantity);
        if (remaining.signum() == 0) {
            holdingRepo.delete(h);
            stockRepo.findByHoldingIdOrderByCreatedAtAsc(h.getId()).forEach(stockRepo::delete);
        } else {
            h.setQuantity(remaining);
            holdingRepo.save(h);
            stockRepo.findByHoldingIdOrderByCreatedAtAsc(h.getId()).forEach(ps -> {
                ps.setQuantity(remaining);
                ps.setAvgBuyPrice(h.getAvgBuyPrice());
                stockRepo.save(ps);
            });
        }
        Transaction t = new Transaction();
        t.setUser(p.getUser());
        t.setStockSymbol(s);
        t.setTransactionType("SELL");
        t.setQuantity(quantity);
        t.setPrice(price);
        t.setTotalAmount(price.multiply(quantity));
        t.setRealizedProfitLoss(pnl);
        Transaction saved = transactionRepo.save(t);
        publisher.publish(saved);
        return saved;
    }

    @Transactional
    public void removeStock(String email, UUID id) {
        PortfolioStock ps = stockRepo.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        if (!ps.getPortfolio().getUser().getEmail().equalsIgnoreCase(email))
            throw new RuntimeException("Access denied");
        stockRepo.delete(ps);
    }
}
