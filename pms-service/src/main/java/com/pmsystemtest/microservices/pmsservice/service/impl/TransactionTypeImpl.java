package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.entity.TransactionType;
import com.pmsystemtest.microservices.pmsservice.repository.TransactionTypeRepository;
import com.pmsystemtest.microservices.pmsservice.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTypeImpl implements TransactionTypeService {

    private final TransactionTypeRepository theTransactionTypeRepository;

    @Override
    public List<TransactionType> findAll() {
        return theTransactionTypeRepository.findAll();
    }
}
