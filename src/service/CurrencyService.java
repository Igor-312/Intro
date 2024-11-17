package service;

import java.util.HashMap;
import java.util.Map;

public class CurrencyService implements CurrencyServiceInterface {
    private Map<String, Double> exchangeRates;

    public CurrencyService() {
        this.exchangeRates = new HashMap<>();
        // Инициализация с тремя валютами
        this.exchangeRates.put("USD", 1.0); // Базовая валюта
        this.exchangeRates.put("EUR", 0.85);
        this.exchangeRates.put("JPY", 110.0);
    }

    @Override
    public Map<String, Double> showExchangeRates() {
        return new HashMap<>(exchangeRates); // Возвращаем копию курсов валют
    }

    @Override
    public void changeCurrencyRate(String currency, Double rate) {
        if (currency == null || rate == null || rate <= 0) {
            throw new IllegalArgumentException("Invalid currency or rate");
        }
        exchangeRates.put(currency, rate);
    }

    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        if (fromCurrency == null || toCurrency == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid currency or amount");
        }
        Double fromRate = exchangeRates.get(fromCurrency);
        Double toRate = exchangeRates.get(toCurrency);

        if (fromRate == null || toRate == null) {
            throw new IllegalArgumentException("Unsupported currency");
        }

        return amount * (toRate / fromRate);
    }

    public static void main(String[] args) {
        CurrencyService service = new CurrencyService();

        System.out.println("Курсы валют: " + service.showExchangeRates());

        service.changeCurrencyRate("EUR", 0.90);
        System.out.println("Измененные курсы валют: " + service.showExchangeRates());

        double convertedAmount = service.convertCurrency("USD", "EUR", 100);
        System.out.println("Конвертированная сумма: " + convertedAmount);
    }
}
