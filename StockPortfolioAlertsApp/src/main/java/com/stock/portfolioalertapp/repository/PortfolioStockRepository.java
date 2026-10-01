package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.PortfolioStock;
import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
import java.util.*;

public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, UUID> {
    List<PortfolioStock> findByPortfolioId(UUID portfolioId);

    Optional<PortfolioStock> findByPortfolioIdAndHoldingId(UUID portfolioId, UUID holdingId);

    List<PortfolioStock> findByHoldingIdOrderByCreatedAtAsc(UUID holdingId);

    @Query("select coalesce(sum(p.quantity),0) from PortfolioStock p where p.holding.id = :holdingId")
    BigDecimal sumAllocatedQuantity(UUID holdingId);
}
