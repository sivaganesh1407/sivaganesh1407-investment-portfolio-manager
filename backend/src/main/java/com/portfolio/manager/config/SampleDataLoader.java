package com.portfolio.manager.config;

import com.portfolio.manager.model.Investment;
import com.portfolio.manager.model.InvestmentType;
import com.portfolio.manager.model.Portfolio;
import com.portfolio.manager.repository.InvestmentRepository;
import com.portfolio.manager.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Loads sample portfolios and investments on startup for demo/screenshots.
 * Active only when not running tests.
 */
@Component
@Profile("!test")
@RequiredArgsConstructor
@Slf4j
public class SampleDataLoader implements CommandLineRunner {

    private final PortfolioRepository portfolioRepository;
    private final InvestmentRepository investmentRepository;

    @Override
    public void run(String... args) {
        if (portfolioRepository.count() > 0) {
            return; // already loaded
        }
        log.info("Loading sample data for demo...");

        Portfolio retirement = portfolioRepository.save(Portfolio.builder()
                .name("Retirement")
                .owner("John Doe")
                .createdDate(LocalDate.of(2024, 1, 1))
                .build());
        Portfolio growth = portfolioRepository.save(Portfolio.builder()
                .name("Growth")
                .owner("John Doe")
                .createdDate(LocalDate.of(2024, 3, 15))
                .build());
        Portfolio crypto = portfolioRepository.save(Portfolio.builder()
                .name("Crypto Portfolio")
                .owner("Jane Smith")
                .createdDate(LocalDate.of(2024, 6, 1))
                .build());
        Portfolio dividend = portfolioRepository.save(Portfolio.builder()
                .name("Dividend Income")
                .owner("Jane Smith")
                .createdDate(LocalDate.of(2024, 9, 10))
                .build());

        List<Investment> investments = List.of(
                // Retirement
                inv(retirement, "AAPL", InvestmentType.STOCK, new BigDecimal("50"), new BigDecimal("145"), new BigDecimal("178"), LocalDate.of(2024, 1, 10), "Core holding"),
                inv(retirement, "VOO", InvestmentType.ETF, new BigDecimal("25"), new BigDecimal("380"), new BigDecimal("420"), LocalDate.of(2024, 2, 1), "S&P 500"),
                inv(retirement, "BND", InvestmentType.BOND, new BigDecimal("100"), new BigDecimal("72"), new BigDecimal("74"), LocalDate.of(2024, 1, 15), null),
                // Growth
                inv(growth, "MSFT", InvestmentType.STOCK, new BigDecimal("20"), new BigDecimal("350"), new BigDecimal("410"), LocalDate.of(2024, 4, 1), null),
                inv(growth, "GOOGL", InvestmentType.STOCK, new BigDecimal("15"), new BigDecimal("140"), new BigDecimal("165"), LocalDate.of(2024, 5, 1), null),
                inv(growth, "QQQ", InvestmentType.ETF, new BigDecimal("10"), new BigDecimal("385"), new BigDecimal("445"), LocalDate.of(2024, 3, 20), "Nasdaq"),
                // Crypto
                inv(crypto, "BTC", InvestmentType.CRYPTO, new BigDecimal("0.5"), new BigDecimal("42000"), new BigDecimal("45000"), LocalDate.of(2024, 6, 5), null),
                inv(crypto, "ETH", InvestmentType.CRYPTO, new BigDecimal("5"), new BigDecimal("2200"), new BigDecimal("2400"), LocalDate.of(2024, 7, 1), null),
                inv(crypto, "SOL", InvestmentType.CRYPTO, new BigDecimal("50"), new BigDecimal("95"), new BigDecimal("180"), LocalDate.of(2024, 8, 1), null),
                // Dividend
                inv(dividend, "JPM", InvestmentType.STOCK, new BigDecimal("30"), new BigDecimal("155"), new BigDecimal("168"), LocalDate.of(2024, 9, 15), "Bank"),
                inv(dividend, "VZ", InvestmentType.STOCK, new BigDecimal("100"), new BigDecimal("38"), new BigDecimal("42"), LocalDate.of(2024, 10, 1), "Telecom"),
                inv(dividend, "SCHD", InvestmentType.ETF, new BigDecimal("40"), new BigDecimal("72"), new BigDecimal("78"), LocalDate.of(2024, 9, 20), "Dividend ETF")
        );

        investmentRepository.saveAll(investments);
        log.info("Sample data loaded: {} portfolios, {} investments", 4, investments.size());
    }

    private static Investment inv(Portfolio p, String symbol, InvestmentType type,
                                  BigDecimal qty, BigDecimal purchase, BigDecimal current,
                                  LocalDate date, String notes) {
        return Investment.builder()
                .portfolio(p)
                .symbol(symbol)
                .type(type)
                .quantity(qty)
                .purchasePrice(purchase)
                .currentPrice(current)
                .purchaseDate(date)
                .notes(notes)
                .build();
    }
}
