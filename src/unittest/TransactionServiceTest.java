package unittest;

import models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TransactionRepository;
import service.TransactionService;
import service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    private TransactionService transactionService;
    private UserService userServiceMock;

    @BeforeEach
    public void setUp() {
        userServiceMock = mock(UserService.class);
        TransactionRepository transactionRepository = new TransactionRepository(userServiceMock);
        transactionService = new TransactionService(transactionRepository, userServiceMock);

        when(userServiceMock.getAccountsByUserId(1)).thenReturn(Collections.emptyList());
    }

    @Test
    public void testAddMoney() {
        transactionService.addMoney(1, 100.0);
        // проверьте ваши утверждения здесь
    }

    @Test
    public void testWithdrawMoney() {
        transactionService.addMoney(1, 100.0);
        transactionService.withdrawMoney(1, 50.0);
        // проверьте ваши утверждения здесь
    }

    @Test
    public void testExchangeMoney() {
        transactionService.exchangeMoney(100.0, "USD", "EUR");
        // проверьте ваши утверждения здесь
    }

    @Test
    public void testShowHistory() {
        List<Transaction> history = transactionService.showHistory().get(1);
        assertNotNull(history);
    }
}
