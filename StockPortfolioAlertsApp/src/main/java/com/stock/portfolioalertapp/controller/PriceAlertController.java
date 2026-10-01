package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.dto.PriceAlertRequest;
import com.stock.portfolioalertapp.entity.PriceAlert;
import com.stock.portfolioalertapp.service.PriceAlertService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/alerts")
public class PriceAlertController {
    private final PriceAlertService service;

    public PriceAlertController(PriceAlertService s) {
        service = s;
    }

    @PostMapping
    public PriceAlert create(Authentication a, @RequestBody PriceAlertRequest r) {
        return service.create(a.getName(), r.getStockSymbol(), r.getAlertType(), r.getTargetPrice());
    }

    @GetMapping
    public List<PriceAlert> get(Authentication a) {
        return service.mine(a.getName());
    }

    @DeleteMapping("/{id}")
    public String delete(Authentication a, @PathVariable UUID id) {
        service.delete(a.getName(), id);
        return "Alert deleted successfully";
    }
}
