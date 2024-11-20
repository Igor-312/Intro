package unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.AccountService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private AccountService accountService;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        accountRepository = new AccountRepository();
        userService = new UserService();
        transactionRepository = new TransactionRepository(userService);
        accountService = new AccountService(accountRepository, transactionRepository);
    }

    @Test
    public void testCreateAccountUSD() {
        accountService.createAccountUSD(1);
        assertNotNull(accountRepository.getAccountById(1));
    }

    @Test
    public void testCreateAccountEUR() {
        accountService.createAccountEUR(1);
        assertNotNull(accountRepository.getAccountById(1));
    }

    @Test
    public void testCreateAccountBTC() {
        accountService.createAccountBTC(1);
        assertNotNull(accountRepository.getAccountById(1));
    }

    @Test
    public void testDeposit() {
        accountService.createAccountUSD(1);
        accountService.deposit(1, 100.0);
        assertEquals(100.0, accountRepository.getAccountById(1).getBalance());
    }

    @Test
    public void testWithdraw() {
        accountService.createAccountUSD(1);
        accountService.deposit(1, 100.0);
        accountService.withdraw(1, 50.0);
        assertEquals(50.0, accountRepository.getAccountById(1).getBalance());
    }

    @Test
    public void testDeleteAccount() {
        accountService.createAccountUSD(1);
        accountService.deleteAccount(1);
        assertNull(accountRepository.getAccountById(1));
    }

    @Test
    public void testShowBalance() {
        accountService.createAccountUSD(1);
        accountService.deposit(1, 100.0);
        assertNotNull(accountService.showBalance(1));
    }

    @Test
    public void testMyAccounts() {
        accountService.createAccountUSD(1);
        assertNotNull(accountService.myAccounts());
    }
}
