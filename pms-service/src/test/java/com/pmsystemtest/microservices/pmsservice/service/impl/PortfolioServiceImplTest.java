package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.entity.Currency;
import com.pmsystemtest.microservices.pmsservice.entity.Portfolio;
import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;
import com.pmsystemtest.microservices.pmsservice.repository.CurrencyRepository;
import com.pmsystemtest.microservices.pmsservice.repository.PortfolioRepository;
import com.pmsystemtest.microservices.pmsservice.repository.ShareTransactionRepository;
import com.pmsystemtest.microservices.pmsservice.request.PortfolioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PortfolioServiceImplTest {

    private PortfolioServiceImpl portfolioService;
    private  PortfolioRepository thePortfolioRepository;
    private  ShareTransactionRepository theTransactionRepository;
    private  CurrencyRepository theCurrencyRepository;

    @BeforeEach
    void setUp() {
        thePortfolioRepository = Mockito.mock(PortfolioRepository.class);
        theTransactionRepository = Mockito.mock(ShareTransactionRepository.class);
        theCurrencyRepository = Mockito.mock(CurrencyRepository.class);
        portfolioService = new PortfolioServiceImpl(thePortfolioRepository, theTransactionRepository, theCurrencyRepository);
    }

    @Test
    public void whenCreatePortfolioCalledWithValidRequest_itShouldReturnValidPortfolio() {
        Long userId = 1L;
        PortfolioRequest portfolioRequest = PortfolioRequest.builder()
                .name("MyPortfolio")
                .currencyId(4L)
                .initialChangeOfCost(BigInteger.ONE)
                .build();

        Currency currency = Currency.builder()
                .id(4L)
                .name("TRY")
                .build();

        given(theCurrencyRepository.findById(portfolioRequest.getCurrencyId())).willReturn(Optional.of(currency));

        given(thePortfolioRepository.save(any(Portfolio.class))).willAnswer(invocation -> invocation.getArgument(0));

        given(theTransactionRepository.save(any(ShareTransaction.class))).willAnswer(invocation -> invocation.getArgument(0));

        Portfolio createdPortfolio = portfolioService.createPortfolio(portfolioRequest, userId);

        //verification
        assertNotNull(createdPortfolio);
        assertEquals(userId, createdPortfolio.getUserId());
        assertEquals(portfolioRequest.getName(), createdPortfolio.getName());
        assertEquals(currency, createdPortfolio.getCurrency());

        // share transaction

        assertNotNull(createdPortfolio.getShareTransactionList());
        assertEquals(1, createdPortfolio.getShareTransactionList().size());

        // share transaction verification
        ShareTransaction shareTransaction = createdPortfolio.getShareTransactionList().get(0);
        assertNotNull(shareTransaction);
        assertEquals(createdPortfolio, shareTransaction.getPortfolio());
        assertEquals(BigDecimal.valueOf(100), shareTransaction.getChangeOfQuantity());
        assertEquals(portfolioRequest.getInitialChangeOfCost(), shareTransaction.getChangeOfCost());
        assertNotNull(shareTransaction.getTime());

        // verify

        verify(theTransactionRepository, times(1)).save(any(ShareTransaction.class));
        verify(thePortfolioRepository, times(1)).save(any(Portfolio.class));
        verify(theCurrencyRepository, times(1)).findById(portfolioRequest.getCurrencyId());

    }
}