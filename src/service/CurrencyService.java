package service;

import repository.CurrencyRepo;
import java.util.Map;

public class CurrencyService implements CurrencyServiceInterface {
    private final CurrencyRepo currencyRepo;

    public CurrencyService() {
        this.currencyRepo = new CurrencyRepo();
    }

    @Override
    public Map<String, Double> showExchangeRates() {
        return currencyRepo.getAllExchangeRates();
    }

    @Override
    public void changeCurrencyRate(String currency, Double rate) {
        currencyRepo.addOrUpdateExchangeRate(currency, rate);
    }

    @Override
    public Double getExchangeRate(String currency) {
        return currencyRepo.getExchangeRate(currency);
    }

    @Override
    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        if (fromCurrency == null || toCurrency == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid currency or amount");
        }
        Double fromRate = currencyRepo.getExchangeRate(fromCurrency);
        Double toRate = currencyRepo.getExchangeRate(toCurrency);

        if (fromRate == null || toRate == null) {
            throw new IllegalArgumentException("Unsupported currency");
        }

        return amount * (toRate / fromRate);
    }
}
