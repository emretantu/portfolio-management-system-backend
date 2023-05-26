package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.entity.TransactionType;
import com.pmsystemtest.microservices.pmsservice.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction-types")
@RequiredArgsConstructor
@CrossOrigin
public class TransactionTypeController {

    private final TransactionTypeService theTransactionTypeService;

    @GetMapping
    public List<TransactionType> retrieveAllTransactionTypes(){
        return theTransactionTypeService.findAll();
    }
}
