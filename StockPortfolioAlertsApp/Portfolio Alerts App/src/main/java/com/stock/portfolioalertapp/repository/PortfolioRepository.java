package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.Portfolio;
import com.stock.portfolioalertapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {

    Optional<Portfolio> findByIdAndUser(
            UUID id,
            User user
    );
}