package com.portfolio.manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request body for creating or updating an investment (portfolio id comes from path).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentRequest {

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotNull(message = "Investment type is required")
    private InvestmentType type;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.000001", message = "Quantity must be positive")
    private BigDecimal quantity;

    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "0", message = "Purchase price must be non-negative")
    private BigDecimal purchasePrice;

    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;

    /** Optional: current price for value tracking and return calculation. */
    @DecimalMin(value = "0", message = "Current price must be non-negative")
    private BigDecimal currentPrice;

    private String notes;
}
