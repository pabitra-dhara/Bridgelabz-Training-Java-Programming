package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.dto.*;
import com.stock.portfolioalertapp.entity.Portfolio;
import com.stock.portfolioalertapp.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    private final PortfolioService service;
    private final HoldingService holdingService;

    public PortfolioController(PortfolioService s, HoldingService h) {
        service = s;
        holdingService = h;
    }

    @PostMapping
    public Portfolio create(Authentication a, @RequestBody PortfolioCreateRequest r) {
        return service.createPortfolioByEmail(a.getName(), r.getName());
    }

    @GetMapping
    public List<Portfolio> get(Authentication a) {
        return service.getUserPortfoliosByEmail(a.getName());
    }

    @DeleteMapping("/{portfolioId}")
    public String delete(Authentication a, @PathVariable UUID portfolioId) {
        service.deletePortfolio(a.getName(), portfolioId);
        return "Portfolio deleted successfully";
    }

    @GetMapping("/summary")
    public PortfolioSummaryResponse summary(Authentication a) {
        return holdingService.getSummary(a.getName());
    }
}
