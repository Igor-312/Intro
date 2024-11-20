package unittest;

import models.Account;
import models.Currency;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AccountRepoInterface;
import repository.AccountRepository;
import service.AccountService;
import service.AccountServiceInterface;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    private AccountServiceInterface accountService;
    private AccountRepoInterface accountRepo;
    private User testUser;

    @BeforeEach
    void setUp() {
        AccountService.resetAccountIdCounter();
        accountRepo = new AccountRepository();
        ((AccountRepository) accountRepo).clearAccounts(); // Очистка репозитория
        accountService = new AccountService(accountRepo);

        // Создание тестового пользователя
        testUser = new User("test@example.com", "password123");

        // Создание тестовых аккаунтов
        Account account1 = new Account(1, new Currency("USD", "US Dollar"), 100.0, testUser);
        Account account2 = new Account(2, new Currency("EUR", "Euro"), 200.0, testUser);
        accountRepo.createAccount(account1);
        accountRepo.createAccount(account2);
    }

    @Test
    void testCreateAccountUSD() {
        accountService.createAccountUSD();
        Account createdAccount = accountRepo.getAccountById(3);  // Новый ID будет 3

        if (createdAccount == null) {
            System.out.println("Account creation failed. createdAccount is null for USD.");
        } else {
            System.out.println("createdAccount (USD): " + createdAccount);
        }

        assertNotNull(createdAccount);
        assertEquals("USD", createdAccount.getCurrency().getCode());
        assertEquals(0.0, createdAccount.getBalance());
    }

    @Test
    void testCreateAccountEUR() {
        accountService.createAccountEUR();
        Account createdAccount = accountRepo.getAccountById(3);  // Новый ID будет 3

        if (createdAccount == null) {
            System.out.println("Account creation failed. createdAccount is null for EUR.");
        } else {
            System.out.println("createdAccount (EUR): " + createdAccount);
        }

        assertNotNull(createdAccount);
        assertEquals("EUR", createdAccount.getCurrency().getCode());
        assertEquals(0.0, createdAccount.getBalance());
    }

    @Test
    void testCreateAccountBTC() {
        accountService.createAccountBTC();
        Account createdAccount = accountRepo.getAccountById(3);  // Новый ID будет 3

        if (createdAccount == null) {
            System.out.println("Account creation failed. createdAccount is null for BTC.");
        } else {
            System.out.println("createdAccount (BTC): " + createdAccount);
        }

        assertNotNull(createdAccount);
        assertEquals("BTC", createdAccount.getCurrency().getCode());
        assertEquals(0.0, createdAccount.getBalance());
    }

    @Test
    void testGetAccountById() {
        Account account = accountService.getAccountById(1);
        assertNotNull(account);
        assertEquals(1, account.getAccountId());
        assertEquals("USD", account.getCurrency().getCode());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testDeposit() {
        accountService.deposit(1, 50.0);
        Account account = accountRepo.getAccountById(1);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        accountService.withdraw(1, 50.0);
        Account account = accountRepo.getAccountById(1);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void testDeleteAccount() {
        accountService.deleteAccount(1);
        Account account = accountRepo.getAccountById(1);
        assertNull(account);
    }

    @Test
    void testShowBalance() {
        Map<Integer, List<Account>> balance = accountService.showBalance(1);
        assertNotNull(balance);
        assertEquals(1, balance.size());
        assertEquals(100.0, balance.get(1).get(0).getBalance());
    }

    @Test
    void testMyAccounts() {
        Map<Integer, List<Account>> accounts = accountService.myAccounts();
        assertNotNull(accounts);
        assertEquals(2, accounts.get(0).size());
    }

    @Test
    void testWithdrawInsufficientBalance() {
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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.deleteAccount(3);
        });

        assertEquals("Account not found with ID: 3", exception.getMessage());
    }
}
