package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.Holding;
import com.stock.portfolioalertapp.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HoldingRepository extends JpaRepository<Holding, UUID> {

    Optional<Holding> findByPortfolioAndStockSymbol(
            Portfolio portfolio,
            String stockSymbol
    );
}