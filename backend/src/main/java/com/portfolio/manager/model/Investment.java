package com.portfolio.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A single investment position within a portfolio (e.g. 10 shares of AAPL, 0.5 BTC).
 */
@Entity
@Table(name = "investment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonIgnore
    private Portfolio portfolio;

    @Column(nullable = false)
    private String symbol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvestmentType type;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal quantity;

    @Column(name = "purchase_price", nullable = false, precision = 19, scale = 6)
    private BigDecimal purchasePrice;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    /** Optional: set for tracking current value and return %; null means use book value only. */
    @Column(name = "current_price", precision = 19, scale = 6)
    private BigDecimal currentPrice;

    @Column(length = 500)
    private String notes;

    /** For API response: avoid serializing full portfolio and lazy-load issues. */
    public Long getPortfolioId() {
        return portfolio != null ? portfolio.getId() : null;
    }
}
