package com.portfolio.manager.service;

import com.portfolio.manager.model.Portfolio;
import com.portfolio.manager.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for portfolio operations.
 * Services encapsulate business logic, validation, and orchestration;
 * they sit between controllers and repositories so that the API layer stays thin.
 */
@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Transactional
    public Portfolio create(Portfolio portfolio) {
        if (portfolio.getCreatedDate() == null) {
            portfolio.setCreatedDate(LocalDate.now());
        }
        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    public Optional<Portfolio> findById(Long id) {
        return portfolioRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        portfolioRepository.deleteById(id);
    }
}
