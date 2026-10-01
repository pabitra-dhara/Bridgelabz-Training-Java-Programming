package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;

    @GetMapping("/trending")
    public String trending() {
        return marketService.getTrendingStocks();
    }

    @GetMapping("/quote/{symbol}")
    public String quote(@PathVariable String symbol) {
        return marketService.getQuote(symbol);
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        return marketService.searchStock(query);
    }
}
