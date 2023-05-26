package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.dto.TransactionDTO;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import com.pmsystemtest.microservices.pmsservice.proxy.UserProxy;
import com.pmsystemtest.microservices.pmsservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@CrossOrigin
public class TransactionController {

    private final TransactionService theTransactionService;
    private final UserProxy theUserProxy;



    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDTO> findAllTransactionsByUserId(@PathVariable Long userId){
        boolean status = theUserProxy.isExistUser(userId);
        if(!status){
            throw new RuntimeException("hatali giris");
        }
        return theTransactionService.findAllTransactionsByUserId(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse> createTransaction(@PathVariable Long userId , @Valid @RequestBody TransactionDTO transactionDTO){
        // check if user exists
        boolean status = theUserProxy.isExistUser(userId);
        if(!status){
            throw new RuntimeException("hatali giris");
        }

        SuccessResponse successResponse = null;
        if(theTransactionService.createTransaction(transactionDTO, userId) != null){
            successResponse = SuccessResponse.builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Transaction created successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<SuccessResponse> deleteTransaction(@PathVariable Long transactionId){
        SuccessResponse successResponse = null;
        if(theTransactionService.deleteTransactionById(transactionId) != null){
            successResponse = SuccessResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Transaction deleted successfully with id - " + transactionId)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
        
    }
}
