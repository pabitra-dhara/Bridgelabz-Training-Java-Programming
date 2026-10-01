package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, UUID> {
    List<PriceAlert> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<PriceAlert> findByActiveTrueAndTriggeredFalse();
}
