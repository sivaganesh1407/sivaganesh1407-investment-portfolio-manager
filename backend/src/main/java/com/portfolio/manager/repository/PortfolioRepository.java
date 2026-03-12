package com.portfolio.manager.repository;

import com.portfolio.manager.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Portfolio entities.
 * Repositories provide a data-access abstraction: they expose CRUD and custom queries
 * without writing SQL. JpaRepository adds methods like findAll(), findById(), save(), deleteById().
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
