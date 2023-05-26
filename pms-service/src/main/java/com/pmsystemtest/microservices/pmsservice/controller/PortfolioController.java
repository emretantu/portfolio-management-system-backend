package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.dto.PortfolioDTO;
import com.pmsystemtest.microservices.pmsservice.dto.PortfolioShareTransactionDTO;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import com.pmsystemtest.microservices.pmsservice.proxy.UserProxy;
import com.pmsystemtest.microservices.pmsservice.request.PortfolioRequest;
import com.pmsystemtest.microservices.pmsservice.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
@CrossOrigin
public class PortfolioController {

    private final PortfolioService thePortfolioService;
    private final UserProxy theUserProxy;



    /*@GetMapping
    public List<Portfolio> retrieveAllPortfolios(){
        return thePortfolioService.findAll();
    }*/

    /*@GetMapping("/{portfolio_id}")
    public Portfolio retrievePortfolio(@PathVariable Long portfolio_id){
        return thePortfolioService.findById(portfolio_id);
    }*/

    /*@GetMapping("/all")
    public List<PortfolioShareTransactionDTO> getAllPortfolioShareTransactions(){
        return thePortfolioService.getAllPortfolioShareTransactionDtos();
    }*/

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PortfolioShareTransactionDTO> retrievePortfolioByUser(@PathVariable Long userId){
        // check if user exists by connect user-info-service

        boolean status = theUserProxy.isExistUser(userId);
        if(!status){
            throw new RuntimeException("hatali giris");
        }

        return thePortfolioService.getAllPortfolioShareTransactionDtos(userId);



        //return thePortfolioService.findByUserId(user_id);
        //return portfolio;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse> createPortfolio(@Valid @RequestBody PortfolioRequest portfolioRequest, @PathVariable Long userId){
        // check if user exists
        boolean status = theUserProxy.isExistUser(userId);
        if(!status){
            throw new RuntimeException("hatali giris");
        }
        SuccessResponse successResponse = null;
        if(thePortfolioService.createPortfolio(portfolioRequest, userId) != null){
            successResponse = SuccessResponse.builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Portfolio created successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);

    }

    @PatchMapping("/{portfolioId}")
    public ResponseEntity<SuccessResponse> updatePortfolio(@PathVariable Long portfolioId, @RequestBody PortfolioDTO portfolioDTO){
        SuccessResponse successResponse = null;
        if(thePortfolioService.updatePortfolio(portfolioDTO, portfolioId) != null){
            successResponse = SuccessResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Portfolio updated successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }




}
