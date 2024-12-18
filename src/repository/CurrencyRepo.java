package repository;

import java.util.HashMap;
import java.util.Map;

public class CurrencyRepo implements CurrencyRepoInterface {
    private Map<String, Double> exchangeRates;

    public CurrencyRepo() {
        this.exchangeRates = new HashMap<>();
        // Инициализация с базовыми курсами валют
        this.exchangeRates.put("USD", 1.0); // Базовая валюта
        this.exchangeRates.put("EUR", 0.85);
        this.exchangeRates.put("BTC", 90000.0); // Цена 1 Bitcoin в USD
    }

    @Override
    public Map<String, Double> getAllExchangeRates() {
        return exchangeRates; // Возвращаем оригинальную карту курсов валют
    }

    @Override
    public void addOrUpdateExchangeRate(String currency, Double rate) {
        if (currency == null || rate == null || rate <= 0) {
            throw new IllegalArgumentException("Invalid currency or rate");
        }
        if (!exchangeRates.containsKey(currency)) {
            throw new IllegalArgumentException("Currency not found");
        }
        exchangeRates.put(currency, rate);
    }

    @Override
    public Double getExchangeRate(String currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        Double rate = exchangeRates.get(currency);
        if (rate == null) {
            throw new IllegalArgumentException("Unsupported currency");
        }
        return rate;
    }
}
