package com.portfolio.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.manager.model.Portfolio;
import com.portfolio.manager.model.PortfolioSummary;
import com.portfolio.manager.service.PortfolioAnalyticsService;
import com.portfolio.manager.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests for Portfolio REST API using MockMvc.
 */
@WebMvcTest(PortfolioController.class)
class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PortfolioService portfolioService;

    @MockBean
    private PortfolioAnalyticsService portfolioAnalyticsService;

    @Test
    void create_returns201AndBody() throws Exception {
        Portfolio request = Portfolio.builder().name("Retirement").owner("john").build();
        Portfolio created = Portfolio.builder()
                .id(1L)
                .name("Retirement")
                .owner("john")
                .createdDate(LocalDate.now())
                .build();
        when(portfolioService.create(any(Portfolio.class))).thenReturn(created);

        mockMvc.perform(post("/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Retirement"))
                .andExpect(jsonPath("$.owner").value("john"));

        verify(portfolioService).create(any(Portfolio.class));
    }

    @Test
    void getAll_returnsList() throws Exception {
        List<Portfolio> list = List.of(
                Portfolio.builder().id(1L).name("P1").owner("u").createdDate(LocalDate.now()).build()
        );
        when(portfolioService.findAll()).thenReturn(list);

        mockMvc.perform(get("/portfolio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("P1"));

        verify(portfolioService).findAll();
    }

    @Test
    void getById_returns200WhenFound() throws Exception {
        Portfolio p = Portfolio.builder().id(1L).name("P").owner("u").createdDate(LocalDate.now()).build();
        when(portfolioService.findById(1L)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/portfolio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("P"));

        verify(portfolioService).findById(1L);
    }

    @Test
    void getById_returns404WhenNotFound() throws Exception {
        when(portfolioService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/portfolio/999"))
                .andExpect(status().isNotFound());

        verify(portfolioService).findById(999L);
    }

    @Test
    void getSummary_returns200WhenFound() throws Exception {
        PortfolioSummary summary = PortfolioSummary.builder()
                .portfolioId(1L)
                .portfolioName("P")
                .investmentCount(2)
                .totalCost(new BigDecimal("1000.00"))
                .totalCurrentValue(new BigDecimal("1200.00"))
                .returnPercentage(new BigDecimal("20.0000"))
                .build();
        when(portfolioAnalyticsService.getSummary(1L)).thenReturn(Optional.of(summary));

        mockMvc.perform(get("/portfolio/1/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.portfolioId").value(1))
                .andExpect(jsonPath("$.portfolioName").value("P"))
                .andExpect(jsonPath("$.investmentCount").value(2))
                .andExpect(jsonPath("$.totalCost").value(1000.00))
                .andExpect(jsonPath("$.totalCurrentValue").value(1200.00));

        verify(portfolioAnalyticsService).getSummary(1L);
    }

    @Test
    void getSummary_returns404WhenNotFound() throws Exception {
        when(portfolioAnalyticsService.getSummary(999L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/portfolio/999/summary")).andExpect(status().isNotFound());
    }

    @Test
    void deleteById_returns204() throws Exception {
        mockMvc.perform(delete("/portfolio/1"))
                .andExpect(status().isNoContent());

        verify(portfolioService).deleteById(1L);
    }
}
