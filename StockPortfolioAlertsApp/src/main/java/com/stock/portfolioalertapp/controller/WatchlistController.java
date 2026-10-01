package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.dto.WatchlistCreateRequest;
import com.stock.portfolioalertapp.entity.Watchlist;
import com.stock.portfolioalertapp.entity.WatchlistCollection;
import com.stock.portfolioalertapp.service.WatchlistService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/watchlists")
public class WatchlistController {
    private final WatchlistService service;

    public WatchlistController(WatchlistService s) {
        service = s;
    }

    @PostMapping
    public WatchlistCollection create(Authentication a, @RequestBody WatchlistCreateRequest r) {
        return service.createList(a.getName(), r.getName());
    }

    @GetMapping
    public List<WatchlistCollection> lists(Authentication a) {
        return service.getLists(a.getName());
    }

    @GetMapping("/{watchlistId}/stocks")
    public List<Watchlist> stocks(Authentication a, @PathVariable UUID watchlistId) {
        return service.getStocks(a.getName(), watchlistId);
    }

    @PostMapping("/{watchlistId}/stocks")
    public Watchlist add(Authentication a, @PathVariable UUID watchlistId, @RequestBody Watchlist w) {
        return service.addStock(a.getName(), watchlistId, w);
    }

    @PostMapping("/{watchlistId}/stocks/{symbol}")
    public Watchlist addLive(Authentication a, @PathVariable UUID watchlistId, @PathVariable String symbol) {
        return service.addLiveStock(a.getName(), watchlistId, symbol);
    }

    @DeleteMapping("/{watchlistId}")
    public String deleteList(Authentication a, @PathVariable UUID watchlistId) {
        service.deleteList(a.getName(), watchlistId);
        return "Watchlist deleted successfully";
    }

    @DeleteMapping("/stocks/{stockId}")
    public String deleteStock(Authentication a, @PathVariable UUID stockId) {
        service.removeStock(a.getName(), stockId);
        return "Stock removed from watchlist successfully";
    }
}
