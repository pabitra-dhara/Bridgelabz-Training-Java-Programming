package com.stock.portfolioalertapp.service;

import com.stock.portfolioalertapp.entity.*;
import com.stock.portfolioalertapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PortfolioService {
    private final PortfolioRepository repo;
    private final UserRepository users;

    public PortfolioService(PortfolioRepository r, UserRepository u) {
        repo = r;
        users = u;
    }

    private User user(String e) {
        return users.findByEmail(e).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Portfolio createPortfolioByEmail(String e, String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Portfolio name is required");
        Portfolio p = new Portfolio();
        p.setPortfolioName(name.trim());
        p.setUser(user(e));
        return repo.save(p);
    }

    public List<Portfolio> getUserPortfoliosByEmail(String e) {
        return repo.findByUserId(user(e).getId());
    }

    @Transactional
    public void deletePortfolio(String email, UUID id) {
        Portfolio p = repo.findByIdAndUserId(id, user(email).getId()).orElseThrow(() -> new RuntimeException("Portfolio not found or access denied"));
        repo.delete(p);
    }
}
