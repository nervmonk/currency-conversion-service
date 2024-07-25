package com.dwikyryan.microservices.currency_conversion_service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {
    private CurrencyExchangeProxy proxy;

    @Autowired
    public CurrencyConversionController(CurrencyExchangeProxy proxy){
        this.proxy = proxy;
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);
        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " feign");
    }
}
