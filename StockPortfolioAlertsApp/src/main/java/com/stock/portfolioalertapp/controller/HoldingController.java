package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.dto.*;
import com.stock.portfolioalertapp.entity.*;
import com.stock.portfolioalertapp.service.HoldingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {
    private final HoldingService service;

    public HoldingController(HoldingService s) {
        service = s;
    }

    @GetMapping
    public List<Holding> get(Authentication a) {
        return service.getUserHoldings(a.getName());
    }

    @PostMapping("/buy")
    public TradeResponse buy(Authentication a, @RequestBody BuyStockRequest r) {
        return to(service.buyStock(a.getName(), r.getStockSymbol(), r.getQuantity()));
    }

    @PostMapping("/sell")
    public TradeResponse sell(Authentication a, @RequestBody SellStockRequest r) {
        return to(service.sellStock(a.getName(), r.getStockSymbol(), r.getQuantity()));
    }

    @GetMapping("/summary")
    public PortfolioSummaryResponse summary(Authentication a) {
        return service.getSummary(a.getName());
    }

    private TradeResponse to(Transaction t) {
        return new TradeResponse(t.getTransactionType(), t.getStockSymbol(), t.getQuantity(), t.getPrice(), t.getTotalAmount(), t.getRealizedProfitLoss(), t.getTransactionAt());
    }
}
