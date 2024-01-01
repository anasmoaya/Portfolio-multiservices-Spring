package com.protfolio.portfolio_service.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.protfolio.portfolio_service.entities.Portfolio;
import com.protfolio.portfolio_service.repositories.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;
    @InjectMocks
    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindAll_thenRetrievePortfolios() {
        List<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(new Portfolio());
        portfolios.add(new Portfolio());

        when(portfolioRepository.findAll()).thenReturn(portfolios);

        List<Portfolio> foundPortfolios = portfolioService.findAll();

        assertNotNull(foundPortfolios);
        assertEquals(2, foundPortfolios.size());
        verify(portfolioRepository, times(1)).findAll();
    }

    @Test
    void whenFindById_thenRetrievePortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        Optional<Portfolio> found = portfolioService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(portfolio.getId(), found.get().getId());
        verify(portfolioRepository, times(1)).findById(1L);
    }

    @Test
    void whenSavePortfolio_thenSuccess() {
        Portfolio portfolio = new Portfolio();

        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(portfolio);

        Portfolio savedPortfolio = portfolioService.save(portfolio);

        assertNotNull(savedPortfolio);
        verify(portfolioRepository, times(1)).save(any(Portfolio.class));
    }
    @Test
    void whenUpdatePortfolio_thenSuccess() {
        Portfolio existingPortfolio = new Portfolio();
        existingPortfolio.setId(1L);

        Portfolio updatedPortfolio = new Portfolio();
        updatedPortfolio.setAboutMe("Updated Info");

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(existingPortfolio));
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(updatedPortfolio);

        Portfolio savedPortfolio = portfolioService.update(1L, updatedPortfolio);

        assertNotNull(savedPortfolio);
        assertEquals("Updated Info", savedPortfolio.getAboutMe());
        verify(portfolioRepository, times(1)).findById(1L);
        verify(portfolioRepository, times(1)).save(any(Portfolio.class));
    }
    @Test
    void whenDeletePortfolio_thenSuccess() {
        Long id = 1L;

        doNothing().when(portfolioRepository).deleteById(id);

        portfolioService.delete(id);

        verify(portfolioRepository, times(1)).deleteById(id);
    }


}
