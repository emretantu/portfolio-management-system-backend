package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.entity.Currency;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.CurrencyNotFoundException;
import com.pmsystemtest.microservices.pmsservice.repository.CurrencyRepository;
import com.pmsystemtest.microservices.pmsservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository theCurrencyRepository;

    @Override
    public List<Currency> findAll() {
        return theCurrencyRepository.findAll();
    }

    @Override
    public Currency findById(Long currencyId) {
        Optional<Currency> currencyOptional = theCurrencyRepository.findById(currencyId);
        Currency currency = null;
        if(currencyOptional.isPresent()){
            currency = currencyOptional.get();
        }else{
            throw new CurrencyNotFoundException();
        }
        return currency;
    }

    @Override
    public Currency createCurrency(Currency currency) {
        currency.setId(0L);
        return theCurrencyRepository.save(currency);
    }

    @Override
    public Currency updateCurrency(Currency currency) {
        return theCurrencyRepository.save(currency);
    }
}
