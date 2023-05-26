package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.entity.TransactionType;

import java.util.List;

public interface TransactionTypeService {

    List<TransactionType> findAll();
}
