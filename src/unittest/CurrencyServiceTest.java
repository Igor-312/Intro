package unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
<<<<<<< HEAD
import repository.CurrencyRepo;
import service.CurrencyService;
import service.CurrencyServiceInterface;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyServiceTest {
    private CurrencyServiceInterface currencyService;
    private CurrencyRepo currencyRepo;

    @BeforeEach
    void setUp() {
        currencyRepo = mock(CurrencyRepo.class);
        currencyService = new CurrencyService(currencyRepo);
=======
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
>>>>>>> 78c2f5dfd7c10f85759431a15e5f464081383d62
    }

    @Test
    void testShowExchangeRates() {
<<<<<<< HEAD
        Map<String, Double> mockRates = new HashMap<>();
        mockRates.put("USD", 1.0);
        mockRates.put("EUR", 0.85);
        when(currencyRepo.getAllExchangeRates()).thenReturn(mockRates);

        Map<String, Double> rates = currencyService.showExchangeRates();
        assertNotNull(rates);
        assertEquals(2, rates.size());
        assertEquals(1.0, rates.get("USD"));
        assertEquals(0.85, rates.get("EUR"));
=======
        Map<String, Double> exchangeRates = currencyService.showExchangeRates();
        assertNotNull(exchangeRates);
        assertEquals(3, exchangeRates.size());
        assertEquals(1.0, exchangeRates.get("USD"));
        assertEquals(0.85, exchangeRates.get("EUR"));
        assertEquals(90000.0, exchangeRates.get("BTC"));
>>>>>>> 78c2f5dfd7c10f85759431a15e5f464081383d62
    }

    @Test
    void testChangeCurrencyRate() {
<<<<<<< HEAD
        currencyService.changeCurrencyRate("GBP", 0.75);
        verify(currencyRepo, times(1)).addOrUpdateExchangeRate("GBP", 0.75);
    }

    @Test
    void testGetExchangeRate() {
        when(currencyRepo.getExchangeRate("USD")).thenReturn(1.0);

        Double rate = currencyService.getExchangeRate("USD");
        assertNotNull(rate);
        assertEquals(1.0, rate);
=======
        currencyService.changeCurrencyRate("EUR", 0.90);
        Double rate = currencyService.getExchangeRate("EUR");
        assertEquals(0.90, rate);
>>>>>>> 78c2f5dfd7c10f85759431a15e5f464081383d62
    }

    @Test
    void testConvertCurrency() {
<<<<<<< HEAD
        when(currencyRepo.getExchangeRate("USD")).thenReturn(1.0);
        when(currencyRepo.getExchangeRate("EUR")).thenReturn(0.85);

        double result = currencyService.convertCurrency("USD", "EUR", 100.0);
        assertEquals(85.0, result);
    }

    @Test
    void testConvertCurrencyWithInvalidAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyService.convertCurrency("USD", "EUR", -100.0);
        });

        assertEquals("Invalid currency or amount", exception.getMessage());
    }

    @Test
    void testConvertCurrencyWithUnsupportedCurrency() {
        when(currencyRepo.getExchangeRate("USD")).thenReturn(1.0);
        when(currencyRepo.getExchangeRate("EUR")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyService.convertCurrency("USD", "EUR", 100.0);
        });

        assertEquals("Unsupported currency", exception.getMessage());
=======
        double amount = currencyService.convertCurrency("USD", "EUR", 100);
        assertEquals(85.0, amount);
    }

    @Test
    void testConvertCurrencyInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            currencyService.convertCurrency("USD", "EUR", -100);
        });
>>>>>>> 78c2f5dfd7c10f85759431a15e5f464081383d62
    }
}
