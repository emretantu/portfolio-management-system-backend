package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.entity.Currency;
import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;

import java.util.List;

public interface ShareTransactionService {

    List<ShareTransaction> findAll();

    ShareTransaction findById(Long share_transaction_id);

    ShareTransaction createShareTransaction(ShareTransaction shareTransaction);

    ShareTransaction updateShareTransaction(ShareTransaction shareTransaction);
}
