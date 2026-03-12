package com.portfolio.manager.service;

import com.portfolio.manager.model.*;
import com.portfolio.manager.repository.InvestmentRepository;
import com.portfolio.manager.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

/**
 * Computes portfolio value (book and current) and performance analytics.
 */
@Service
@RequiredArgsConstructor
public class PortfolioAnalyticsService {

    private static final int SCALE = 4;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    private final PortfolioRepository portfolioRepository;
    private final InvestmentRepository investmentRepository;

    /**
     * Get value and performance summary for a portfolio.
     */
    public Optional<PortfolioSummary> getSummary(Long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .map(portfolio -> {
                    List<Investment> investments = investmentRepository.findByPortfolio_Id(portfolioId);
                    BigDecimal totalCost = BigDecimal.ZERO;
                    BigDecimal totalCurrentValue = BigDecimal.ZERO;
                    EnumMap<InvestmentType, BigDecimal> valueByType = new EnumMap<>(InvestmentType.class);

                    for (Investment inv : investments) {
                        BigDecimal cost = inv.getQuantity().multiply(inv.getPurchasePrice()).setScale(SCALE, ROUNDING);
                        BigDecimal currentValue = inv.getCurrentPrice() != null
                                ? inv.getQuantity().multiply(inv.getCurrentPrice()).setScale(SCALE, ROUNDING)
                                : cost;
                        totalCost = totalCost.add(cost);
                        totalCurrentValue = totalCurrentValue.add(currentValue);
                        valueByType.merge(inv.getType(), currentValue, BigDecimal::add);
                    }

                    BigDecimal returnPct = totalCost.compareTo(BigDecimal.ZERO) == 0
                            ? null
                            : totalCurrentValue.subtract(totalCost)
                                    .divide(totalCost, SCALE + 2, ROUNDING)
                                    .multiply(BigDecimal.valueOf(100));

                    List<AllocationItem> allocation = new ArrayList<>();
                    if (totalCurrentValue.compareTo(BigDecimal.ZERO) > 0) {
                        for (InvestmentType type : valueByType.keySet()) {
                            BigDecimal val = valueByType.get(type);
                            BigDecimal pct = val.multiply(BigDecimal.valueOf(100)).divide(totalCurrentValue, SCALE, ROUNDING);
                            allocation.add(new AllocationItem(type, val, pct));
                        }
                        allocation.sort((a, b) -> b.getPercentage().compareTo(a.getPercentage()));
                    }

                    return PortfolioSummary.builder()
                            .portfolioId(portfolio.getId())
                            .portfolioName(portfolio.getName())
                            .investmentCount(investments.size())
                            .totalCost(totalCost)
                            .totalCurrentValue(totalCurrentValue)
                            .returnPercentage(returnPct)
                            .allocationByType(allocation)
                            .build();
                });
    }
}
