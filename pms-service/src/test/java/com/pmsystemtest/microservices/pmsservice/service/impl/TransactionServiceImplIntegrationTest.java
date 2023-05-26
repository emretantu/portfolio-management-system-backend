package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.dto.TransactionDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Asset;
import com.pmsystemtest.microservices.pmsservice.entity.Portfolio;
import com.pmsystemtest.microservices.pmsservice.entity.Transaction;
import com.pmsystemtest.microservices.pmsservice.entity.TransactionType;
import com.pmsystemtest.microservices.pmsservice.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionServiceImplIntegrationTest {

    @Autowired
    private TransactionServiceImpl transactionService;
    @Autowired
    private TransactionRepository theTransactionRepository;
    @Autowired
    private PortfolioRepository thePortfolioRepository;
    @Autowired
    private TransactionTypeRepository theTransactionTypeRepository;
    @Autowired
    private AssetRepository theAssetRepository;
    @Autowired
    private ShareTransactionRepository theShareTransactionRepository;
    @Autowired
    private  TransactionBufferRepository theTransactionBufferRepository;

    @Test
    public void whenCreateTransactionCalledWithValidRequest_itShouldReturnValidTransaction(){
        Long userId = 1L;
        Long portfolioId = 1L;
        Long assetId = 1L;
        Long transactionTypeId = 1L;
        BigDecimal changeOfQuantity = BigDecimal.valueOf(10.0);
        BigInteger changeOfMainCost = BigInteger.ONE;
        BigDecimal changeOfPortfolioCost = BigDecimal.valueOf(1000.0);

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .portfolioId(portfolioId)
                .assetId(assetId)
                .transactionTypeId(transactionTypeId)
                .changeOfQuantity(changeOfQuantity)
                .changeOfMainCost(changeOfMainCost)
                .changeOfPortfolioCost(changeOfPortfolioCost)
                .build();

        Portfolio portfolio = Portfolio.builder()
                .id(portfolioId)
                .build();
        thePortfolioRepository.save(portfolio);

        Asset asset = Asset.builder()
                .id(assetId)
                .build();
        theAssetRepository.save(asset);

        TransactionType transactionType = TransactionType.builder()
                .id(transactionTypeId)
                .build();
        theTransactionTypeRepository.save(transactionType);

        Transaction createdTransaction = transactionService.createTransaction(transactionDTO, userId);

        List<Transaction> allTransactions = theTransactionRepository.findAll();
        assertThat(allTransactions).hasSize(1);
        assertThat(allTransactions.get(0)).isEqualTo(createdTransaction);
    }


}
