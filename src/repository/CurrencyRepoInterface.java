package repository;

import java.util.Map;

public interface CurrencyRepoInterface {
    Map<String, Double> getAllExchangeRates();
    void addOrUpdateExchangeRate(String currency, Double rate);
    Double getExchangeRate(String currency);
}
