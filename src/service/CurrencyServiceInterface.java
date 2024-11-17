package service;

import java.util.Map;

public interface CurrencyServiceInterface {

    Map<String, String> showExchangeRates();

    void changeCurrencyRate(String currency, Double rate);
}
