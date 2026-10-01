package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {
    List<Watchlist> findByWatchlistIdOrderByCreatedAtAsc(UUID watchlistId);

    boolean existsByWatchlistIdAndStockNameIgnoreCase(UUID watchlistId, String stockName);
}
