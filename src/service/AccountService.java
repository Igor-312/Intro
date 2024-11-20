package service;

import models.Account;
import models.CurrencyCode;
import repository.AccountRepository;

import java.util.List;
import java.util.Map;

public class AccountService implements AccountServiceInterface {

    private final AccountRepository accountRepository;

    // Конструктор по умолчанию
    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    // Конструктор с параметром
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccountUSD(int userId) {
        accountRepository.createAccountForUser(userId, CurrencyCode.USD, 0.0);
    }

    @Override
    public void createAccountEUR(int userId) {
        accountRepository.createAccountForUser(userId, CurrencyCode.EUR, 0.0);
    }

    @Override
    public void createAccountBTC(int userId) {
        accountRepository.createAccountForUser(userId, CurrencyCode.BTC, 0.0);
    }

    @Override
    public Account getAccountById(int accountId) {
        return accountRepository.getAccountById(accountId);
    }

    @Override
    public void deposit(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        accountRepository.updateAccountBalance(accountId, amount);
    }

    @Override
    public void withdraw(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        Account account = accountRepository.getAccountById(accountId);
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        accountRepository.updateAccountBalance(accountId, -amount);
    }

    @Override
    public void deleteAccount(int accountId) {
        accountRepository.deleteAccountById(accountId);
    }

    @Override
    public Map<Integer, List<Account>> showBalance(int userId) {
        return accountRepository.getAccountsByUserId(userId);
    }

    @Override
    public Map<Integer, List<Account>> myAccounts() {
        return accountRepository.getAllAccounts();
    }
}
