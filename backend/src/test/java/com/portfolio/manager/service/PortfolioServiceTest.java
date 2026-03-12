package com.portfolio.manager.service;

import com.portfolio.manager.model.Portfolio;
import com.portfolio.manager.repository.PortfolioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PortfolioService.
 */
@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @Test
    void create_setsCreatedDateWhenNull() {
        Portfolio input = Portfolio.builder()
                .name("Retirement")
                .owner("john")
                .createdDate(null)
                .build();
        Portfolio saved = Portfolio.builder()
                .id(1L)
                .name("Retirement")
                .owner("john")
                .createdDate(LocalDate.now())
                .build();
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(saved);

        Portfolio result = portfolioService.create(input);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(input.getCreatedDate()).isEqualTo(LocalDate.now());
        verify(portfolioRepository).save(input);
    }

    @Test
    void create_preservesCreatedDateWhenProvided() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        Portfolio input = Portfolio.builder()
                .name("Growth")
                .owner("jane")
                .createdDate(date)
                .build();
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(input);

        portfolioService.create(input);

        assertThat(input.getCreatedDate()).isEqualTo(date);
        verify(portfolioRepository).save(input);
    }

    @Test
    void findAll_returnsFromRepository() {
        List<Portfolio> list = List.of(
                Portfolio.builder().id(1L).name("A").owner("u1").createdDate(LocalDate.now()).build()
        );
        when(portfolioRepository.findAll()).thenReturn(list);

        List<Portfolio> result = portfolioService.findAll();

        assertThat(result).hasSize(1).element(0).extracting(Portfolio::getName).isEqualTo("A");
        verify(portfolioRepository).findAll();
    }

    @Test
    void findById_returnsOptionalWhenFound() {
        Portfolio p = Portfolio.builder().id(1L).name("P").owner("u").createdDate(LocalDate.now()).build();
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Portfolio> result = portfolioService.findById(1L);

        assertThat(result).isPresent().get().extracting(Portfolio::getId).isEqualTo(1L);
        verify(portfolioRepository).findById(1L);
    }

    @Test
    void findById_returnsEmptyWhenNotFound() {
        when(portfolioRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Portfolio> result = portfolioService.findById(999L);

        assertThat(result).isEmpty();
        verify(portfolioRepository).findById(999L);
    }

    @Test
    void deleteById_callsRepository() {
        portfolioService.deleteById(1L);
        verify(portfolioRepository).deleteById(eq(1L));
    }
}
