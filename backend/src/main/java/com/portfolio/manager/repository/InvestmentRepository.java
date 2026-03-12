package com.portfolio.manager.repository;

import com.portfolio.manager.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for Investment entities.
 */
@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByPortfolio_Id(Long portfolioId);
}
