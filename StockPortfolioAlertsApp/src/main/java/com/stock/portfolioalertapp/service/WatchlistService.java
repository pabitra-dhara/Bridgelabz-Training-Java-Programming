package com.stock.portfolioalertapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.portfolioalertapp.entity.*;
import com.stock.portfolioalertapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WatchlistService {
    private final WatchlistRepository stocks;
    private final WatchlistCollectionRepository lists;
    private final UserRepository users;
    private final ObjectMapper mapper;
    private final MarketService market;

    public WatchlistService(WatchlistRepository s, WatchlistCollectionRepository l, UserRepository u, ObjectMapper m, MarketService market) {
        stocks = s;
        lists = l;
        users = u;
        mapper = m;
        this.market = market;
    }

    private User user(String email) {
        return users.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public WatchlistCollection createList(String email, String name) {
        User u = user(email);
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Watchlist name is required");
        if (lists.existsByUserIdAndNameIgnoreCase(u.getId(), name.trim()))
            throw new IllegalArgumentException("Watchlist name already exists");
        WatchlistCollection w = new WatchlistCollection();
        w.setUser(u);
        w.setName(name.trim());
        return lists.save(w);
    }

    public List<WatchlistCollection> getLists(String email) {
        return lists.findByUserId(user(email).getId());
    }

    public List<Watchlist> getStocks(String email, UUID listId) {
        lists.findByIdAndUserId(listId, user(email).getId()).orElseThrow(() -> new RuntimeException("Watchlist not found or access denied"));
        return stocks.findByWatchlistIdOrderByCreatedAtAsc(listId);
    }

    public Watchlist addStock(String email, UUID listId, Watchlist w) {
        User u = user(email);
        WatchlistCollection list = lists.findByIdAndUserId(listId, u.getId()).orElseThrow(() -> new RuntimeException("Watchlist not found or access denied"));
        if (w.getStockName() == null || w.getStockName().isBlank())
            throw new IllegalArgumentException("Stock symbol is required");
        String s = w.getStockName().trim().toUpperCase();
        if (stocks.existsByWatchlistIdAndStockNameIgnoreCase(listId, s))
            throw new IllegalArgumentException("Stock already exists in this watchlist");
        w.setWatchlist(list);
        w.setUser(u);
        w.setStockName(s);
        if (w.getCurrency() == null || w.getCurrency().isBlank()) w.setCurrency("INR");
        return stocks.save(w);
    }

    public Watchlist addLiveStock(String email, UUID listId, String symbol) {
        User u = user(email);
        WatchlistCollection list = lists.findByIdAndUserId(listId, u.getId()).orElseThrow(() -> new RuntimeException("Watchlist not found or access denied"));
        String s = symbol.trim().toUpperCase();
        if (stocks.existsByWatchlistIdAndStockNameIgnoreCase(listId, s))
            throw new IllegalArgumentException("Stock already exists in this watchlist");
        try {
            JsonNode root = mapper.readTree(market.getQuote(s));
            Watchlist w = new Watchlist();
            w.setWatchlist(list);
            w.setUser(u);
            w.setStockName(s);
            w.setCompanyName(root.path("companyName").asText(s));
            w.setIsin(root.path("companyProfile").path("isin").asText(""));
            w.setSector(root.path("industry").asText("Unknown"));
            w.setCurrency("INR");
            w.setWeek52High(decimal(root.path("yearHigh")));
            w.setWeek52Low(decimal(root.path("yearLow")));
            return stocks.save(w);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add live stock to watchlist", e);
        }
    }

    @Transactional
    public void deleteList(String email, UUID id) {
        WatchlistCollection l = lists.findByIdAndUserId(id, user(email).getId()).orElseThrow(() -> new RuntimeException("Watchlist not found or access denied"));
        lists.delete(l);
    }

    public void removeStock(String email, UUID id) {
        Watchlist w = stocks.findById(id).orElseThrow(() -> new RuntimeException("Watchlist stock not found"));
        if (!w.getUser().getEmail().equalsIgnoreCase(email)) throw new RuntimeException("Access denied");
        stocks.delete(w);
    }

    private BigDecimal decimal(JsonNode n) {
        try {
            return n == null || n.isMissingNode() || n.isNull() ? null : new BigDecimal(n.asText().replace(",", ""));
        } catch (Exception e) {
            return null;
        }
    }
}
