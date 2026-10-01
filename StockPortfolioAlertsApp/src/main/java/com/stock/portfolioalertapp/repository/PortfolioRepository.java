package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    List<Portfolio> findByUserId(UUID userId);

    Optional<Portfolio> findByIdAndUserId(UUID id, UUID userId);
}
