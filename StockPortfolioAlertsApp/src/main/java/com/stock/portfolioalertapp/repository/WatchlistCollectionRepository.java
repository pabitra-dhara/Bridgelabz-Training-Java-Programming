package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.WatchlistCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface WatchlistCollectionRepository extends JpaRepository<WatchlistCollection, UUID> {
    List<WatchlistCollection> findByUserId(UUID userId);

    Optional<WatchlistCollection> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByUserIdAndNameIgnoreCase(UUID userId, String name);
}
