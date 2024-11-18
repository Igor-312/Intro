package service;

import java.util.Map;

public interface CurrencyServiceInterface {
    Map<String, Double> showExchangeRates();
    void changeCurrencyRate(String currency, Double rate);
    Double getExchangeRate(String currency);
    double convertCurrency(String fromCurrency, String toCurrency, double amount);
}
