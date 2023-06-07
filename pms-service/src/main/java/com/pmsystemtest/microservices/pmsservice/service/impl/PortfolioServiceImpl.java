package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.config.TokenValidator;
import com.pmsystemtest.microservices.pmsservice.dto.PortfolioDTO;
import com.pmsystemtest.microservices.pmsservice.dto.PortfolioShareTransactionDTO;
import com.pmsystemtest.microservices.pmsservice.dto.ShareTransactionDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Currency;
import com.pmsystemtest.microservices.pmsservice.entity.Portfolio;
import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.CurrencyNotFoundException;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.PortfolioNotFoundException;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import com.pmsystemtest.microservices.pmsservice.repository.CurrencyRepository;
import com.pmsystemtest.microservices.pmsservice.repository.PortfolioRepository;
import com.pmsystemtest.microservices.pmsservice.repository.ShareTransactionRepository;
import com.pmsystemtest.microservices.pmsservice.request.PortfolioRequest;
import com.pmsystemtest.microservices.pmsservice.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository thePortfolioRepository;
    private final ShareTransactionRepository theTransactionRepository;
    private final CurrencyRepository theCurrencyRepository;
    private final TokenValidator tokenValidator;

    @Override
    public List<Portfolio> findAll() {
        return thePortfolioRepository.findAll();
    }

    @Override
    public Portfolio findById(Long portfolioId) {
        Optional<Portfolio> portfolioOptional = thePortfolioRepository.findById(portfolioId);
        Portfolio portfolio = null;
        if(portfolioOptional.isPresent()){
            portfolio = portfolioOptional.get();
        }else{
            throw new PortfolioNotFoundException();
        }
        return portfolio;
    }

    @Override
    public Portfolio createPortfolio(PortfolioRequest portfolioRequest, Long userId) {

        // create a currency
        Optional<Currency> currencyOptional = theCurrencyRepository.findById(portfolioRequest.getCurrencyId());
        Currency currency = null;
        if(currencyOptional.isPresent()){
            currency = currencyOptional.get();
        }else{
            throw new CurrencyNotFoundException();
        }

        // create a portfolio
        Portfolio portfolio = Portfolio.builder()
                .userId(userId)
                .name(portfolioRequest.getName())
                .currency(currency)
                .status(true)
                .build();
        thePortfolioRepository.save(portfolio);

        BigDecimal changeOfQuantity = BigDecimal.valueOf(100000);

        //create share transaction
        ShareTransaction shareTransaction = ShareTransaction.builder()
                .portfolio(portfolio)
                .changeOfQuantity(changeOfQuantity)
                .changeOfCost(portfolioRequest.getInitialChangeOfCost())
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        theTransactionRepository.save(shareTransaction);

        List<ShareTransaction> shareTransactionList = new ArrayList<>();
        shareTransactionList.add(shareTransaction);
        portfolio.setShareTransactionList(shareTransactionList);
        return portfolio;

    }

    @Override
    public ResponseEntity<SuccessResponse> updatePortfolio(PortfolioDTO portfolioDTO, Long portfolioId, String token) {

        Portfolio portfolio = thePortfolioRepository.findUserIdById(portfolioId).orElseThrow(PortfolioNotFoundException::new);
        if(!tokenValidator.checkTokenByUserId(portfolio.getUserId(), token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user ID");
        }

        if(portfolioDTO.getStatus() != null){
            portfolio.setStatus(portfolioDTO.getStatus());
        }

        if(portfolioDTO.getName() != null){
            portfolio.setName(portfolioDTO.getName());
        }

        if(portfolioDTO.getCurrencyId() != null){
            Optional<Currency> currencyOptional = theCurrencyRepository.findById(portfolioDTO.getCurrencyId());
            Currency currency = null;
            if(currencyOptional.isPresent()){
                currency = currencyOptional.get();
            }else{
                throw new CurrencyNotFoundException();
            }
            portfolio.setCurrency(currency);
        }

        portfolio = thePortfolioRepository.save(portfolio);

        SuccessResponse successResponse = SuccessResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Portfolio updated successfully")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Override
    public List<Portfolio> findByUserId(Long userId) {
        return thePortfolioRepository.findByUserId(userId);
    }

    @Override
    public List<PortfolioShareTransactionDTO> getAllPortfolioShareTransactionDtos(Long userId) {
        List<Portfolio> portfolios = thePortfolioRepository.findByUserId(userId);
        if(portfolios.isEmpty()){
            throw new PortfolioNotFoundException();
        }
        return getPortfolioTransactionDTOS(portfolios);
    }

    private List<PortfolioShareTransactionDTO> getPortfolioTransactionDTOS(List<Portfolio> portfolios) {
        List<PortfolioShareTransactionDTO> portfolioShareTransactionDTOS = new ArrayList<>();
        for(Portfolio portfolio : portfolios){
            List<ShareTransactionDTO> shareTransactionDTOList = portfolio.getShareTransactionList().stream()
                    .map(transaction -> ShareTransactionDTO.builder()
                            .id(transaction.getId())
                            .changeOfQuantity(transaction.getChangeOfQuantity())
                            .changeOfCost(transaction.getChangeOfCost())
                            .timestamp(new Timestamp(System.currentTimeMillis()))
                            .build())
                    .collect(Collectors.toList());
            PortfolioShareTransactionDTO portfolioShareTransactionDTO = PortfolioShareTransactionDTO.builder()
                    .id(portfolio.getId())
                    .userId(portfolio.getUserId())
                    .name(portfolio.getName())
                    .currencyId(portfolio.getCurrency().getId())
                    .shareTransactions(shareTransactionDTOList)
                    .build();
            portfolioShareTransactionDTOS.add(portfolioShareTransactionDTO);
        }
        return portfolioShareTransactionDTOS;
    }

    @Override
    public List<PortfolioShareTransactionDTO> getAllPortfolioShareTransactionDtos() {
        List<Portfolio> portfolios = thePortfolioRepository.findAll();
        if(portfolios.isEmpty()){
            throw new PortfolioNotFoundException();
        }
        return getPortfolioTransactionDTOS(portfolios);
    }

    @Override
    public List<ShareTransaction> getAllShareTransactionByPortfolioId(Long portfolioId) {
        Optional<Portfolio> portfolioOptional = thePortfolioRepository.findById(portfolioId);
        Portfolio portfolio = null;
        if(portfolioOptional.isPresent()){
            portfolio = portfolioOptional.get();
        }else{
            throw new PortfolioNotFoundException();
        }

        return portfolio.getShareTransactionList();
    }

    @Override
    public Portfolio findUserIdById(Long portfolioId) {
        return thePortfolioRepository.findUserIdById(portfolioId).orElse(null);
    }
}
