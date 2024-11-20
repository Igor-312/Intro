package unittest;

import models.Currency;
import models.CurrencyCode;
import models.Transaction;
import models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TransactionRepository;
import service.TransactionService;
import service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    private TransactionService transactionService;
    private TransactionRepository transactionRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        userService = mock(UserService.class);
        transactionService = new TransactionService(transactionRepository, userService);
    }

    @Test
    void testAddMoney() {
        transactionService.addMoney(1, 100.0);
        verify(transactionRepository, times(1)).addTransaction(eq(1), any(Transaction.class));
    }

    @Test
    void testWithdrawMoney() {
        transactionService.withdrawMoney(1, 100.0);
        verify(transactionRepository, times(1)).addTransaction(eq(1), any(Transaction.class));
    }

    @Test
    void testExchangeMoney() {
        transactionService.exchangeMoney(100.0, CurrencyCode.USD, CurrencyCode.EUR);
        verify(transactionRepository, times(1)).addTransaction(eq(0), any(Transaction.class));
    }

    @Test
    void testShowHistory() {
        Transaction transaction = new Transaction(
                1, 1, 100.0, LocalDateTime.now(), new Currency(CurrencyCode.USD, "US Dollar")
        );
        when(transactionRepository.getAllTransactions()).thenReturn(List.of(transaction));

        Map<Integer, List<Transaction>> history = transactionService.showHistory();
        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals(1, history.get(1).size());
        assertEquals(transaction, history.get(1).get(0));
    }

    @Test
    void testShowUserHistory() {
        Transaction transaction = new Transaction(
                1, 1, 100.0, LocalDateTime.now(), new Currency(CurrencyCode.USD, "US Dollar")
        );
        Account account = new Account(CurrencyCode.USD, 100.0);
        account.setAccountId(1);
        when(transactionRepository.getAllTransactions()).thenReturn(List.of(transaction));
        when(userService.getAccountsByUserId(1)).thenReturn(List.of(account));

        Map<Integer, List<Transaction>> userHistory = transactionService.showUserHistory(1);
        assertNotNull(userHistory);
        assertEquals(1, userHistory.size());
        assertEquals(1, userHistory.get(1).size());
        assertEquals(transaction, userHistory.get(1).get(0));
    }

    @Test
    void testAddMoneyWithNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.addMoney(1, -100.0);
        });

        assertEquals("The amount of money must be greater than zero.", exception.getMessage());
    }

    @Test
    void testWithdrawMoneyWithNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.withdrawMoney(1, -100.0);
        });

        assertEquals("The amount of money must be greater than zero.", exception.getMessage());
    }

    @Test
    void testExchangeMoneyWithNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.exchangeMoney(-100.0, CurrencyCode.USD, CurrencyCode.EUR);
        });

        assertEquals("The amount of money must be greater than zero.", exception.getMessage());
    }
}
