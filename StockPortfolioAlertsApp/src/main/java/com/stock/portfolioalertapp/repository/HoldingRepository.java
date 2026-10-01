package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface HoldingRepository extends JpaRepository<Holding, UUID> {
    List<Holding> findByUserId(UUID userId);

    Optional<Holding> findByUserIdAndStockSymbol(UUID userId, String stockSymbol);
}
