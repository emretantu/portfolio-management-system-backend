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

    /*@PostConstruct
    public void loadData(){
        Currency currency1 = Currency.builder()
                .name("EUR")
                .build();
        Currency currency2 = Currency.builder()
                .name("USD")
                .build();
        Currency currency3 = Currency.builder()
                .name("KRW")
                .build();

        theCurrencyService.createCurrency(currency1);
        theCurrencyService.createCurrency(currency2);
        theCurrencyService.createCurrency(currency3);
    }*/





    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Currency> retrieveAllCurrencies(){
        return theCurrencyService.findAll();
    }

    /*@GetMapping("/{currency_id}")
    public Currency retrieveCurrency(Long currency_id){
        return theCurrencyService.findById(currency_id);
    }*/

    /*@PostMapping
    public Currency createCurrency(@RequestBody Currency currency){
        currency.setId(0L);
        return theCurrencyService.createCurrency(currency);
    }*/

    /*@PutMapping
    public Currency updateCurrency(@RequestBody Currency currency){
        return theCurrencyService.updateCurrency(currency);
    }*/

}
