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
        if (rate <= 0) {
            throw new IllegalArgumentException("Rate must be greater than zero");
        }
        currencyRepo.addOrUpdateExchangeRate(currency, rate);
    }

    @Override
    public Double getExchangeRate(String currency) {
        Double rate = currencyRepo.getExchangeRate(currency);
        if (rate == null || rate <= 0) {
            throw new IllegalArgumentException("Invalid or unsupported currency: " + currency);
        }
        return rate;
    }

    @Override
    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        if (fromCurrency == null || toCurrency == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid currency or amount");
        }
        Double fromRate = getExchangeRate(fromCurrency);
        Double toRate = getExchangeRate(toCurrency);

        return amount * (toRate / fromRate);
    }
}
