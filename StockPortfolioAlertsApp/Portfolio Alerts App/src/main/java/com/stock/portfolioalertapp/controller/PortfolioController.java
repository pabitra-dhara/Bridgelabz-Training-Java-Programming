package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.dto.BuyStockRequest;
import com.stock.portfolioalertapp.entity.Holding;
import com.stock.portfolioalertapp.service.PortfolioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(
            PortfolioService portfolioService
    ) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/{portfolioId}/buy")
    public ResponseEntity<?> buyStock(
            @PathVariable UUID portfolioId,
            @Valid @RequestBody BuyStockRequest request,
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        Holding holding =
                portfolioService.buyStock(
                        portfolioId,
                        email,
                        request
                );

        return ResponseEntity.ok(holding);
    }
}