package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.dto.TransactionDTO;
import com.pmsystemtest.microservices.pmsservice.entity.*;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.*;
import com.pmsystemtest.microservices.pmsservice.repository.*;
import com.pmsystemtest.microservices.pmsservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository theTransactionRepository;
    private final PortfolioRepository thePortfolioRepository;
    private final TransactionTypeRepository theTransactionTypeRepository;
    private final AssetRepository theAssetRepository;
    private final ShareTransactionRepository theShareTransactionRepository;
    private final TransactionBufferRepository theTransactionBufferRepository;
    private final PortfolioServiceImpl thePortfolioService;



    @Override
    public List<TransactionDTO> findAllTransactionsByUserId(Long userId) {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        // find all portfolios by user
        List<Portfolio> portfolios = thePortfolioRepository.findByUserId(userId);

        for(Portfolio portfolio : portfolios){
            //find all transactions
            List<Transaction> transactions = theTransactionRepository.findAllByPortfolioId(portfolio.getId());

            // convert transaction to DTOs
            for(Transaction transaction : transactions){
                TransactionDTO transactionDTO = TransactionDTO.builder()
                        .id(transaction.getId())
                        .portfolioId(transaction.getPortfolio().getId())
                        .assetId(transaction.getAsset().getId())
                        .transactionTypeId(transaction.getTransactionType().getId())
                        .changeOfQuantity(transaction.getChangeOfQuantity())
                        .changeOfMainCost(transaction.getChangeOfMainCost())
                        .changeOfPortfolioCost(transaction.getChangeOfPortfolioCost())
                        .time(transaction.getTime())
                        .build();
                transactionDTOList.add(transactionDTO);
            }
        }
        if(transactionDTOList.isEmpty()){
            throw new TransactionNotFoundException();
        }
        return transactionDTOList;
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO, Long userId) {
        Optional<Portfolio> portfolioOptional = thePortfolioRepository.findByUserIdAndId(userId, transactionDTO.getPortfolioId());
        Portfolio portfolio = null;
        if(portfolioOptional.isPresent()){
            portfolio = portfolioOptional.get();
        }else{
            throw new PortfolioNotFoundException();
        }

        Optional<Asset> assetOptional = theAssetRepository.findByUserIdAndId(userId, transactionDTO.getAssetId());
        Asset asset = null;
        if(assetOptional.isPresent()){
            asset = assetOptional.get();
        }else{
            throw new AssetNotFoundException();
        }

        Optional<TransactionType> transactionTypeOptional = theTransactionTypeRepository.findById(transactionDTO.getTransactionTypeId());
        TransactionType transactionType = null;
        if(transactionTypeOptional.isPresent()){
            transactionType = transactionTypeOptional.get();
        }else{
            throw new TransactionTypeNotFoundException();
        }



        if(transactionType.getId() == 1 || transactionType.getId() == 2){ // deposit
            //create share transaction for portfolio

            List<ShareTransaction> shareTransactionList = thePortfolioService.getAllShareTransactionByPortfolioId(portfolio.getId());

            BigDecimal totalCOQ= BigDecimal.valueOf(0);
            BigInteger totalCOC= BigInteger.valueOf(0);

            for(ShareTransaction shareTransaction : shareTransactionList){
                totalCOQ = totalCOQ.add(shareTransaction.getChangeOfQuantity());
                totalCOC = totalCOC.add(shareTransaction.getChangeOfCost());
            }

            BigDecimal instantSharePrice = new BigDecimal(totalCOC).divide(totalCOQ, 2, RoundingMode.HALF_UP);
            BigDecimal changeOfQuantity = new BigDecimal(transactionDTO.getChangeOfMainCost()).divide(instantSharePrice, 2, RoundingMode.HALF_UP);
            BigInteger changeOfMainCost = transactionDTO.getChangeOfMainCost();

            if(transactionType.getId() == 2){
                changeOfQuantity = changeOfQuantity.multiply(BigDecimal.valueOf(-1));
                changeOfMainCost = changeOfMainCost.multiply(BigInteger.valueOf(-1));
            }

            ShareTransaction shareTransaction = ShareTransaction.builder()
                    .portfolio(portfolio)
                    .changeOfQuantity(changeOfQuantity)
                    .changeOfCost(changeOfMainCost)
                    .time(new Timestamp(System.currentTimeMillis()))
                    .build();
            theShareTransactionRepository.save(shareTransaction);

        }



        Transaction transaction = Transaction.builder()
                .portfolio(portfolio)
                .asset(asset)
                .transactionType(transactionType)
                .changeOfQuantity(transactionDTO.getChangeOfQuantity())
                .changeOfMainCost(transactionDTO.getChangeOfMainCost())
                .changeOfPortfolioCost(transactionDTO.getChangeOfPortfolioCost())
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        transaction = theTransactionRepository.save(transaction);

        return transaction;
    }

    @Override
    public Transaction deleteTransactionById(Long transactionId) {

        Optional<Transaction> transactionOptional = theTransactionRepository.findById(transactionId);
        Transaction transaction = null;
        if(transactionOptional.isPresent()){
            transaction = transactionOptional.get();
        }else{
            throw new TransactionNotFoundException();
        }

        TransactionBuffer transactionBuffer = TransactionBuffer.builder()
                .id(transaction.getId())
                .portfolioId(transaction.getPortfolio().getId())
                .assetId(transaction.getAsset().getId())
                .transactionTypeId(transaction.getTransactionType().getId())
                .changeOfQuantity(transaction.getChangeOfQuantity())
                .changeOfMainCost(transaction.getChangeOfMainCost())
                .changeOfPortfolioCost(transaction.getChangeOfPortfolioCost())
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        theTransactionBufferRepository.save(transactionBuffer);


        theTransactionRepository.deleteById(transactionId);
        return transaction;
    }
}
