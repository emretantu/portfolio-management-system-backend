package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.entity.Currency;
import com.pmsystemtest.microservices.pmsservice.entity.Portfolio;

import java.util.List;

public interface CurrencyService {

    List<Currency> findAll();

    Currency findById(Long currency_id);

    Currency createCurrency(Currency currency);

    Currency updateCurrency(Currency currency);


}
