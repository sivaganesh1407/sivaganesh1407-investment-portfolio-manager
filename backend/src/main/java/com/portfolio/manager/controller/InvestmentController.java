package com.portfolio.manager.controller;

import com.portfolio.manager.model.Investment;
import com.portfolio.manager.model.InvestmentRequest;
import com.portfolio.manager.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST API for investments.
 *
 * POST   /portfolio/{portfolioId}/investments - Create investment in portfolio
 * GET    /portfolio/{portfolioId}/investments - List investments in portfolio
 * GET    /investments/{id}                   - Get investment by id
 * PUT    /investments/{id}                   - Update investment
 * DELETE /investments/{id}                   - Delete investment
 */
@RestController
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    @PostMapping("/portfolio/{portfolioId}/investments")
    public ResponseEntity<Investment> create(
            @PathVariable Long portfolioId,
            @Valid @RequestBody InvestmentRequest request) {
        return investmentService.create(portfolioId, request)
                .map(inv -> ResponseEntity.status(HttpStatus.CREATED).body(inv))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/portfolio/{portfolioId}/investments")
    public List<Investment> listByPortfolio(@PathVariable Long portfolioId) {
        return investmentService.findByPortfolioId(portfolioId);
    }

    @GetMapping("/investments/{id}")
    public ResponseEntity<Investment> getById(@PathVariable Long id) {
        return investmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/investments/{id}")
    public ResponseEntity<Investment> update(
            @PathVariable Long id,
            @Valid @RequestBody InvestmentRequest request) {
        return investmentService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/investments/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        investmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
