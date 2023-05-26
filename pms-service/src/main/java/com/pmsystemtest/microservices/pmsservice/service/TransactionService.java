package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.dto.TransactionDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> findAllTransactionsByUserId(Long userId);

    Transaction createTransaction(TransactionDTO transactionDTO, Long userId);

    Transaction deleteTransactionById(Long transactionId);
}
