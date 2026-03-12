package com.portfolio.manager.service;

import com.portfolio.manager.model.Investment;
import com.portfolio.manager.model.InvestmentRequest;
import com.portfolio.manager.model.Portfolio;
import com.portfolio.manager.repository.InvestmentRepository;
import com.portfolio.manager.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for investment CRUD and listing by portfolio.
 */
@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public Optional<Investment> create(Long portfolioId, InvestmentRequest request) {
        return portfolioRepository.findById(portfolioId)
                .map(portfolio -> {
                    Investment inv = toEntity(portfolio, request);
                    return investmentRepository.save(inv);
                });
    }

    public List<Investment> findByPortfolioId(Long portfolioId) {
        return investmentRepository.findByPortfolioId(portfolioId);
    }

    public Optional<Investment> findById(Long id) {
        return investmentRepository.findById(id);
    }

    @Transactional
    public Optional<Investment> update(Long id, InvestmentRequest request) {
        return investmentRepository.findById(id)
                .map(inv -> {
                    inv.setSymbol(request.getSymbol());
                    inv.setType(request.getType());
                    inv.setQuantity(request.getQuantity());
                    inv.setPurchasePrice(request.getPurchasePrice());
                    inv.setPurchaseDate(request.getPurchaseDate());
                    inv.setCurrentPrice(request.getCurrentPrice());
                    inv.setNotes(request.getNotes());
                    return investmentRepository.save(inv);
                });
    }

    @Transactional
    public void deleteById(Long id) {
        investmentRepository.deleteById(id);
    }

    private static Investment toEntity(Portfolio portfolio, InvestmentRequest req) {
        return Investment.builder()
                .portfolio(portfolio)
                .symbol(req.getSymbol())
                .type(req.getType())
                .quantity(req.getQuantity())
                .purchasePrice(req.getPurchasePrice())
                .purchaseDate(req.getPurchaseDate())
                .currentPrice(req.getCurrentPrice())
                .notes(req.getNotes())
                .build();
    }
}
