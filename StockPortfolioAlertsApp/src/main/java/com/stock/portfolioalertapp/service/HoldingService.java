package com.stock.portfolioalertapp.service;

import com.stock.portfolioalertapp.dto.PortfolioSummaryResponse;
import com.stock.portfolioalertapp.entity.*;
import com.stock.portfolioalertapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.*;
import java.util.*;

@Service
public class HoldingService {
    private final HoldingRepository holdingRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final MarketService marketService;
    private final TradeEventPublisher tradeEventPublisher;
    private final PortfolioStockRepository portfolioStockRepository;

    public HoldingService(HoldingRepository h, TransactionRepository t, UserRepository u, MarketService m, TradeEventPublisher p, PortfolioStockRepository ps) {
        holdingRepository = h;
        transactionRepository = t;
        userRepository = u;
        marketService = m;
        tradeEventPublisher = p;
        portfolioStockRepository = ps;
    }

    private User user(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String sym(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("Stock symbol is required");
        return s.trim().toUpperCase();
    }

    private void q(BigDecimal x) {
        if (x == null || x.signum() <= 0) throw new IllegalArgumentException("Quantity must be positive");
    }

    public List<Holding> getUserHoldings(String email) {
        return holdingRepository.findByUserId(user(email).getId());
    }

    @Transactional
    public Transaction buyStock(String email, String symbol, BigDecimal quantity) {
        q(quantity);
        User u = user(email);
        String s = sym(symbol);
        BigDecimal price = marketService.getCurrentPrice(s);
        Holding h = holdingRepository.findByUserIdAndStockSymbol(u.getId(), s).orElse(null);
        if (h == null) {
            h = new Holding();
            h.setUser(u);
            h.setStockSymbol(s);
            h.setCompanyName(marketService.getCompanyName(s));
            h.setQuantity(quantity);
            h.setAvgBuyPrice(price);
        } else {
            BigDecimal total = h.getQuantity().add(quantity);
            h.setAvgBuyPrice(h.getAvgBuyPrice().multiply(h.getQuantity()).add(price.multiply(quantity)).divide(total, 4, RoundingMode.HALF_UP));
            h.setQuantity(total);
        }
        holdingRepository.save(h);
        Transaction t = new Transaction();
        t.setUser(u);
        t.setStockSymbol(s);
        t.setTransactionType("BUY");
        t.setQuantity(quantity);
        t.setPrice(price);
        t.setTotalAmount(price.multiply(quantity));
        t.setRealizedProfitLoss(BigDecimal.ZERO);
        Transaction saved = transactionRepository.save(t);
        tradeEventPublisher.publish(saved);
        return saved;
    }

    @Transactional
    public Transaction sellStock(String email, String symbol, BigDecimal quantity) {
        q(quantity);
        User u = user(email);
        String s = sym(symbol);
        Holding h = holdingRepository.findByUserIdAndStockSymbol(u.getId(), s).orElseThrow(() -> new IllegalArgumentException("Holding not found for " + s));
        if (quantity.compareTo(h.getQuantity()) > 0)
            throw new IllegalArgumentException("Insufficient holding quantity");
        BigDecimal price = marketService.getCurrentPrice(s);
        BigDecimal pnl = price.subtract(h.getAvgBuyPrice()).multiply(quantity).setScale(4, RoundingMode.HALF_UP);
        BigDecimal rem = h.getQuantity().subtract(quantity);
        if (rem.signum() == 0) {
            holdingRepository.delete(h);
            portfolioStockRepository.findByHoldingIdOrderByCreatedAtAsc(h.getId()).forEach(portfolioStockRepository::delete);
        } else {
            h.setQuantity(rem);
            holdingRepository.save(h);
            portfolioStockRepository.findByHoldingIdOrderByCreatedAtAsc(h.getId()).forEach(ps -> {
                ps.setQuantity(rem);
                ps.setAvgBuyPrice(h.getAvgBuyPrice());
                portfolioStockRepository.save(ps);
            });
        }
        Transaction t = new Transaction();
        t.setUser(u);
        t.setStockSymbol(s);
        t.setTransactionType("SELL");
        t.setQuantity(quantity);
        t.setPrice(price);
        t.setTotalAmount(price.multiply(quantity));
        t.setRealizedProfitLoss(pnl);
        Transaction saved = transactionRepository.save(t);
        tradeEventPublisher.publish(saved);
        return saved;
    }

    public PortfolioSummaryResponse getSummary(String email) {
        User u = user(email);
        List<Holding> hs = holdingRepository.findByUserId(u.getId());
        BigDecimal invested = BigDecimal.ZERO, current = BigDecimal.ZERO;
        List<PortfolioSummaryResponse.HoldingSummary> items = new ArrayList<>();
        for (Holding h : hs) {
            BigDecimal cost = h.getAvgBuyPrice().multiply(h.getQuantity());
            BigDecimal cp = marketService.getCurrentPrice(h.getStockSymbol());
            BigDecimal value = cp.multiply(h.getQuantity());
            BigDecimal pnl = value.subtract(cost);
            invested = invested.add(cost);
            current = current.add(value);
            items.add(new PortfolioSummaryResponse.HoldingSummary(h.getStockSymbol(), h.getQuantity(), h.getAvgBuyPrice(), cp, cost, value, pnl));
        }
        BigDecimal realized = transactionRepository.findByUserIdOrderByTransactionAtDesc(u.getId()).stream().map(Transaction::getRealizedProfitLoss).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal unrealized = current.subtract(invested);
        return new PortfolioSummaryResponse(invested, current, unrealized, realized, unrealized.add(realized), items);
    }
}
