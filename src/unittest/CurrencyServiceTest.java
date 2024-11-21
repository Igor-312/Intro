package unittest;

import org.junit.jupiter.api.Test;
import repository.CurrencyRepo;
import service.CurrencyService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyServiceTest {

    @Test
    public void testShowExchangeRates() {
        CurrencyService currencyService = new CurrencyService();

        Map<String, Double> rates = currencyService.showExchangeRates();
        assertNotNull(rates);
        assertTrue(rates.containsKey("USD"));
        assertTrue(rates.containsKey("EUR"));
        assertTrue(rates.containsKey("BTC"));
    }

    @Test
    public void testChangeCurrencyRate() {
        CurrencyService currencyService = new CurrencyService();

        currencyService.changeCurrencyRate("USD", 1.1);
        assertEquals(1.1, currencyService.getExchangeRate("USD"));
    }

    @Test
    public void testConvertCurrency() {
        CurrencyService currencyService = new CurrencyService();

        double convertedAmount = currencyService.convertCurrency("USD", "EUR", 100);
        assertEquals(85.0, convertedAmount); // Пример, если курс USD к EUR = 0.85
    }
}
