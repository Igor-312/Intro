package service;

import models.Account;
import models.Currency;
import models.User;
import repository.AccountRepoInterface;
import repository.AccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService implements AccountServiceInterface {

    private final AccountRepoInterface accountRepo;

    public AccountService(AccountRepoInterface accountRepo) {
        this.accountRepo = accountRepo;
    }

    public AccountService() {
        this(new AccountRepository());
    }

    public static void resetAccountIdCounter() {
        accountIdCounter = 1;
    }

    @Override
    public Account getAccountById(int accountId) {
        Account account = accountRepo.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }
        return account;
    }

    @Override
    public void deposit(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        Account account = getAccountById(accountId);
        accountRepo.updateAccountBalance(accountId, amount);
    }

    @Override
    public void withdraw(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        Account account = getAccountById(accountId);
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        accountRepo.updateAccountBalance(accountId, -amount);
    }

    @Override
    public void deleteAccount(int accountId) {
        Account account = getAccountById(accountId);
        if (account != null) {
            accountRepo.deleteAccount(accountId);
        } else {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }
    }

    @Override
    public void createAccountUSD() {
        int accountId = generateAccountId();
        System.out.println("Creating account with ID " + accountId + " for USD.");
        Account account = new Account(accountId, new Currency("USD", "US Dollar"), 0.0, new User("default@example.com", "defaultPassword"));
        accountRepo.createAccount(account);
        System.out.println("Account created: " + account);
    }

    @Override
    public void createAccountEUR() {
        int accountId = generateAccountId();
        System.out.println("Creating account with ID " + accountId + " for EUR.");
        Account account = new Account(accountId, new Currency("EUR", "Euro"), 0.0, new User("default@example.com", "defaultPassword"));
        accountRepo.createAccount(account);
        System.out.println("Account created: " + account);
    }

    @Override
    public void createAccountBTC() {
        int accountId = generateAccountId();
        System.out.println("Creating account with ID " + accountId + " for BTC.");
        Account account = new Account(accountId, new Currency("BTC", "Bitcoin"), 0.0, new User("default@example.com", "defaultPassword"));
        accountRepo.createAccount(account);
        System.out.println("Account created: " + account);
    }

    private static int accountIdCounter = 1;

    private int generateAccountId() {
        return accountIdCounter++;
    }

    @Override
    public Map<Integer, List<Account>> showBalance(int accountId) {
        Map<Integer, List<Account>> result = new HashMap<>();
        Account account = getAccountById(accountId);
        if (account != null) {
            result.put(accountId, List.of(account));
        }
        return result;
    }

    @Override
    public Map<Integer, List<Account>> myAccounts() {
        Map<Integer, List<Account>> allAccounts = new HashMap<>();
        List<Account> accounts = accountRepo.getAllAccount();
        allAccounts.put(0, accounts);
        return allAccounts;
    }
}
