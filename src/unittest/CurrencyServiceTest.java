package unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CurrencyService;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurrencyServiceTest {
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        currencyService = new CurrencyService();
    }

    @Test
    void testShowExchangeRates() {
        Map<String, Double> exchangeRates = currencyService.showExchangeRates();
        assertNotNull(exchangeRates);
        assertEquals(3, exchangeRates.size());
        assertEquals(1.0, exchangeRates.get("USD"));
        assertEquals(0.85, exchangeRates.get("EUR"));
        assertEquals(90000.0, exchangeRates.get("BTC"));
    }

    @Test
    void testChangeCurrencyRate() {
        currencyService.changeCurrencyRate("EUR", 0.90);
        Double rate = currencyService.getExchangeRate("EUR");
        assertEquals(0.90, rate);
    }

    @Test
    void testConvertCurrency() {
        double amount = currencyService.convertCurrency("USD", "EUR", 100);
        assertEquals(85.0, amount);
    }

    @Test
    void testConvertCurrencyInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            currencyService.convertCurrency("USD", "EUR", -100);
        });
    }
}
