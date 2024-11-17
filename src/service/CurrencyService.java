package service;

import java.util.Map;

public class CurrencyService implements CurrencyServiceInterface{


    @Override
    public Map<String, String> showExchangeRates() {
        return Map.of();
    }

    @Override
    public void changeCurrencyRate(String currency, Double rate) {

    }
}
