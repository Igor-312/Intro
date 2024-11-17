package repository;

import java.util.HashMap;
import java.util.Map;

public class CurrencyRepo implements CurrencyRepoInterface {
    private Map<String, Double> exchangeRates;

    public CurrencyRepo() {
        this.exchangeRates = new HashMap<>();
        // Инициализация с некоторыми базовыми курсами валют
        this.exchangeRates.put("USD", 1.0); // Базовая валюта
        this.exchangeRates.put("EUR", 0.85);
        this.exchangeRates.put("JPY", 110.0);
    }

    @Override
    public Map<String, Double> getAllExchangeRates() {
        return new HashMap<>(exchangeRates); // Возвращаем копию курсов валют
    }

    @Override
    public void addOrUpdateExchangeRate(String currency, Double rate) {
        if (currency == null || rate == null || rate <= 0) {
            throw new IllegalArgumentException("Invalid currency or rate");
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

    public static void main(String[] args) {
        CurrencyRepo repo = new CurrencyRepo();

        System.out.println("Курсы валют: " + repo.getAllExchangeRates());

        repo.addOrUpdateExchangeRate("EUR", 0.90);
        System.out.println("Измененные курсы валют: " + repo.getAllExchangeRates());

        double rate = repo.getExchangeRate("EUR");
        System.out.println("Курс EUR: " + rate);
    }
}
