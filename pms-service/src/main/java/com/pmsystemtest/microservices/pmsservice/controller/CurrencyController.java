package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.entity.Currency;
import com.pmsystemtest.microservices.pmsservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
@CrossOrigin
public class CurrencyController {

    private final CurrencyService theCurrencyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Currency> retrieveAllCurrencies(){
        return theCurrencyService.findAll();
    }


}
