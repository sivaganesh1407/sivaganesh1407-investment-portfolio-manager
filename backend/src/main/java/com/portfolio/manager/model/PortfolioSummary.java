package com.portfolio.manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Portfolio value and performance summary for analytics.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioSummary {

    private Long portfolioId;
    private String portfolioName;
    private int investmentCount;

    /** Total cost basis (sum of quantity × purchase price). */
    private BigDecimal totalCost;

    /** Total current value (sum of quantity × current price, or cost when current price not set). */
    private BigDecimal totalCurrentValue;

    /** Return percentage: ((totalCurrentValue - totalCost) / totalCost) × 100; null when totalCost is zero. */
    private BigDecimal returnPercentage;

    /** Allocation by investment type (e.g. STOCK 60%, ETF 40%). */
    private List<AllocationItem> allocationByType;
}
