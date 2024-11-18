package unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TransactionRepository;
import repository.TransactionRepoInterface;
import models.Transaction;
import service.TransactionService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    private TransactionService transactionService;
    private TransactionRepoInterface transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService((TransactionRepository) transactionRepository);
    }

    @Test
    void testAddMoney() {
        int accountId = 1;
        double amount = 100.0;

        transactionService.addMoney(accountId, amount);

        verify(transactionRepository, times(1)).addTransaction(eq(accountId), any(Transaction.class));
    }

    @Test
    void testWithdrawMoney() {
        int accountId = 1;
        double amount = 50.0;
        when(transactionRepository.getAccountBalance(accountId)).thenReturn(100.0);

        transactionService.withdrawMoney(accountId, amount);

        verify(transactionRepository, times(1)).addTransaction(eq(accountId), any(Transaction.class));
    }

    @Test
    void testWithdrawMoneyNotEnoughFunds() {
        int accountId = 1;
        double amount = 150.0;
        when(transactionRepository.getAccountBalance(accountId)).thenReturn(100.0);

        transactionService.withdrawMoney(accountId, amount);

        verify(transactionRepository, times(0)).addTransaction(eq(accountId), any(Transaction.class));
    }

    @Test
    void testExchangeMoney() {
        double amount = 100.0;
        String fromCurrency = "USD";
        String toCurrency = "EUR";

        transactionService.exchangeMoney(amount, fromCurrency, toCurrency);

        verify(transactionRepository, times(1)).addTransaction(eq(0), any(Transaction.class));
    }

    @Test
    void testShowHistory() {
        when(transactionRepository.getAllTransactions()).thenReturn(Arrays.asList(
                new Transaction(1, 1, 100.0, LocalDateTime.now(), new models.Currency("USD", "US Dollar")),
                new Transaction(2, 2, 200.0, LocalDateTime.now(), new models.Currency("USD", "US Dollar"))
        ));

        Map<Integer, List<Transaction>> history = transactionService.showHistory();
        assertEquals(2, history.size());
    }

    @Test
    void testShowUserHistory() {
        int userId = 1;
        when(transactionRepository.getAllTransactions()).thenReturn(Arrays.asList(
                new Transaction(1, 1, 100.0, LocalDateTime.now(), new models.Currency("USD", "US Dollar"), userId),
                new Transaction(2, 1, 200.0, LocalDateTime.now(), new models.Currency("USD", "US Dollar"), userId)
        ));

        Map<Integer, List<Transaction>> userHistory = transactionService.showUserHistory(userId);
        assertEquals(1, userHistory.size()); // Количество аккаунтов
        assertEquals(2, userHistory.get(1).size()); // Количество транзакций
    }
}
