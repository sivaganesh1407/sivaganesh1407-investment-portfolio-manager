package com.portfolio.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Investment Portfolio Manager application.
 * Enables component scanning and auto-configuration for the com.portfolio.manager package.
 */
@SpringBootApplication
public class PortfolioManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioManagerApplication.class, args);
    }
}
