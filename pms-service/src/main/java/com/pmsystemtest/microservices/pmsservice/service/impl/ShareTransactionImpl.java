package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;
import com.pmsystemtest.microservices.pmsservice.repository.ShareTransactionRepository;
import com.pmsystemtest.microservices.pmsservice.service.ShareTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareTransactionImpl implements ShareTransactionService {

    private final ShareTransactionRepository theTransactionRepository;

    @Override
    public List<ShareTransaction> findAll() {
        return theTransactionRepository.findAll();
    }

    @Override
    public ShareTransaction findById(Long share_transaction_id) {
        return null;
    }

    @Override
    public ShareTransaction createShareTransaction(ShareTransaction shareTransaction) {
        return null;
    }

    @Override
    public ShareTransaction updateShareTransaction(ShareTransaction shareTransaction) {
        return null;
    }
}
