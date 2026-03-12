package com.portfolio.manager.controller;

import com.portfolio.manager.model.Portfolio;
import com.portfolio.manager.model.PortfolioSummary;
import com.portfolio.manager.service.PortfolioAnalyticsService;
import com.portfolio.manager.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for portfolio operations.
 *
 * POST   /portfolio     - Create a new portfolio (request body: name, owner, optional createdDate)
 * GET    /portfolio     - Return all portfolios
 * GET    /portfolio/{id} - Return a single portfolio by id (404 if not found)
 * DELETE /portfolio/{id} - Delete a portfolio by id (204 on success)
 */
@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioAnalyticsService portfolioAnalyticsService;

    @PostMapping
    public ResponseEntity<Portfolio> create(@Valid @RequestBody Portfolio portfolio) {
        Portfolio created = portfolioService.create(portfolio);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Portfolio> getAll() {
        return portfolioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getById(@PathVariable Long id) {
        return portfolioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Portfolio value and performance summary: total cost, current value, return %, allocation by type. */
    @GetMapping("/{id}/summary")
    public ResponseEntity<PortfolioSummary> getSummary(@PathVariable Long id) {
        return portfolioAnalyticsService.getSummary(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        portfolioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
