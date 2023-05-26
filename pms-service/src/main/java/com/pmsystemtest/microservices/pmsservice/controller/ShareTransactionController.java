package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;
import com.pmsystemtest.microservices.pmsservice.service.ShareTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/share-transactions")
@RequiredArgsConstructor
@CrossOrigin
public class ShareTransactionController {

    private final ShareTransactionService theShareTransactionService;

    @GetMapping
    public List<ShareTransaction> findAll(){
        return theShareTransactionService.findAll();
    }
}
