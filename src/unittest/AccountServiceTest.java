package unittest;

import models.Account;
import models.CurrencyCode;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AccountRepository;
import repository.UserRepository;
import service.AccountService;
import service.AccountServiceInterface;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private AccountServiceInterface accountService;
    private AccountRepository accountRepo;
    private User testUser;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        accountRepo = mock(AccountRepository.class);
        accountService = new AccountService(accountRepo);

        // Создание нового экземпляра UserRepository и очистка пользователей
        userRepository = new UserRepository();
        UserRepository.users.clear();

        // Создание тестового пользователя
        testUser = new User("test@example.com", "password123");
        userRepository.addUser(testUser.getEmail(), testUser.getPassword());

        // Создание тестовых аккаунтов
        Account account1 = new Account(CurrencyCode.USD, 100.0);
        account1.setAccountId(1);
        account1.setUser(testUser);

        Account account2 = new Account(CurrencyCode.EUR, 200.0);
        account2.setAccountId(2);
        account2.setUser(testUser);

        when(accountRepo.getAccountById(1)).thenReturn(account1);
        when(accountRepo.getAccountById(2)).thenReturn(account2);
        when(accountRepo.getAllAccounts()).thenReturn(Map.of(
                testUser.getUserId(), List.of(account1, account2)
        ));
    }

    @Test
    void testCreateAccountUSD() {
        accountService.createAccountUSD(testUser.getUserId());
        verify(accountRepo, times(1)).createAccountForUser(testUser.getUserId(), CurrencyCode.USD, 0.0);
    }

    @Test
    void testCreateAccountEUR() {
        accountService.createAccountEUR(testUser.getUserId());
        verify(accountRepo, times(1)).createAccountForUser(testUser.getUserId(), CurrencyCode.EUR, 0.0);
    }

    @Test
    void testGetAccountById() {
        Account account = accountService.getAccountById(1);
        assertNotNull(account);
        assertEquals(1, account.getAccountId());
        assertEquals(CurrencyCode.USD, account.getCurrency());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testDeposit() {
        accountService.deposit(1, 50.0);
        verify(accountRepo, times(1)).updateAccountBalance(1, 50.0);
    }

    @Test
    void testWithdraw() {
        accountService.withdraw(1, 50.0);
        verify(accountRepo, times(1)).updateAccountBalance(1, -50.0);
    }

    @Test
    void testDeleteAccount() {
        accountService.deleteAccount(1);
        verify(accountRepo, times(1)).deleteAccountById(1);
    }

    @Test
    void testShowBalance() {
        when(accountRepo.getAccountsByUserId(testUser.getUserId())).thenReturn(Map.of(
                testUser.getUserId(), List.of(
                        new Account(CurrencyCode.USD, 100.0),
                        new Account(CurrencyCode.EUR, 200.0)
                )
        ));
        Map<Integer, List<Account>> balance = accountService.showBalance(testUser.getUserId());
        assertNotNull(balance);
        assertEquals(2, balance.get(testUser.getUserId()).size());
    }

    @Test
    void testMyAccounts() {
        when(accountRepo.getAllAccounts()).thenReturn(Map.of(
                testUser.getUserId(), List.of(
                        new Account(CurrencyCode.USD, 100.0),
                        new Account(CurrencyCode.EUR, 200.0)
                )
        ));
        Map<Integer, List<Account>> accounts = accountService.myAccounts();
        assertNotNull(accounts);
        assertEquals(2, accounts.get(testUser.getUserId()).size());
    }

    @Test
    void testWithdrawInsufficientBalance() {
        Account account = new Account(CurrencyCode.USD, 50.0);
        account.setAccountId(1);
        account.setUser(testUser);
        when(accountRepo.getAccountById(1)).thenReturn(account);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(1, 150.0);
        });

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void testDepositInvalidAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(1, -50.0);
        });

        assertEquals("Deposit amount must be positive", exception.getMessage());
    }

    @Test
    void testWithdrawInvalidAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(1, -50.0);
        });

        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    void testDeleteAccountNotFound() {
        doThrow(new IllegalArgumentException("Account not found with ID: 3")).when(accountRepo).deleteAccountById(3);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.deleteAccount(3);
        });

        assertEquals("Account not found with ID: 3", exception.getMessage());
    }
}
