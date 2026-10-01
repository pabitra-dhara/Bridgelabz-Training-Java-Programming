package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.dto.*;
import com.stock.portfolioalertapp.entity.PortfolioStock;
import com.stock.portfolioalertapp.entity.Transaction;
import com.stock.portfolioalertapp.service.PortfolioStockService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/portfolio-stocks")
public class PortfolioStockController {
    private final PortfolioStockService service;

    public PortfolioStockController(PortfolioStockService s) {
        service = s;
    }

    @PostMapping("/add")
    public PortfolioStock add(Authentication a, @RequestBody PortfolioStockRequest r) {
        return service.addHoldingToPortfolio(a.getName(), r.getPortfolioId(), r.getStockSymbol());
    }

    @PostMapping("/sell")
    public Transaction sell(Authentication a, @RequestBody PortfolioStockRequest r) {
        return service.sellFromPortfolio(a.getName(), r.getPortfolioId(), r.getStockSymbol(), r.getQuantity());
    }

    @GetMapping("/{portfolioId}")
    public List<PortfolioStock> get(Authentication a, @PathVariable UUID portfolioId) {
        return service.getPortfolioStocks(a.getName(), portfolioId);
    }

    @DeleteMapping("/{portfolioStockId}")
    public String remove(Authentication a, @PathVariable UUID portfolioStockId) {
        service.removeStock(a.getName(), portfolioStockId);
        return "Stock removed from portfolio successfully";
    }
}
