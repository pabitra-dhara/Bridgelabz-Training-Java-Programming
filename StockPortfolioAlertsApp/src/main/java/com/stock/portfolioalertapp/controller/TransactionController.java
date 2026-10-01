package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.dto.TradeResponse;
import com.stock.portfolioalertapp.entity.Transaction;
import com.stock.portfolioalertapp.entity.User;
import com.stock.portfolioalertapp.repository.TransactionRepository;
import com.stock.portfolioalertapp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionController(TransactionRepository transactionRepository,
                                 UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<TradeResponse> getTransactions(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository
                .findByUserIdOrderByTransactionAtDesc(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TradeResponse toResponse(Transaction t) {
        return new TradeResponse(
                t.getTransactionType(),
                t.getStockSymbol(),
                t.getQuantity(),
                t.getPrice(),
                t.getTotalAmount(),
                t.getRealizedProfitLoss(),
                t.getTransactionAt());
    }
}
