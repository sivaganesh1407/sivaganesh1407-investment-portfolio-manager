package com.portfolio.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * One row in allocation breakdown: type and its percentage of total value.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllocationItem {

    private InvestmentType type;
    private BigDecimal value;
    private BigDecimal percentage;
}
