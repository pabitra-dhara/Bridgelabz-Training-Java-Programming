package com.stock.portfolioalertapp.repository;

import com.stock.portfolioalertapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByUserIdOrderByTransactionAtDesc(UUID userId);

    List<Transaction> findByUserIdAndStockSymbolOrderByTransactionAtDesc(
            UUID userId, String stockSymbol);
}
