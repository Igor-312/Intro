package service;

import models.Account;

import models.Account;
import repository.AccountRepoInterface;
import repository.TransactionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService implements AccountServiceInterface {

    private final AccountRepoInterface accountRepo;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepoInterface accountRepo, TransactionRepository transactionRepository) {
        this.accountRepo = accountRepo;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void createAccountUSD(int userId) {
        accountRepo.createAccount(new Account(userId, "USD", 0.0));
    }

    @Override
    public void createAccountEUR(int userId) {
        accountRepo.createAccount(new Account(userId, "EUR", 0.0));
    }

    @Override
    public void createAccountBTC(int userId) {
        accountRepo.createAccount(new Account(userId, "BTC", 0.0));
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
