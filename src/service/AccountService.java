package service;

import models.Account;

import repository.AccountRepoInterface;
import java.util.List;
import java.util.Map;

import java.util.List;

public class AccountService implements AccountServiceInterface {

    // Репозиторий для работы с аккаунтами
    private AccountRepoInterface accountRepo;

    // Конструктор, принимающий репозиторий
    public AccountService(AccountRepoInterface accountRepo) {
        this.accountRepo = accountRepo;
    }
   
    // Получение аккаунта по ID
    @Override
    public Account getAccountById(int accountId) {
        Account account = accountRepo.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }
        return account;
    }

    // Пополнение баланса
    @Override
    public void deposit(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        Account account = getAccountById(accountId);
        accountRepo.updateAccountBalance(accountId, amount); // Добавляем деньги на баланс
    }

    // Снятие средств
    @Override
    public void withdraw(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        Account account = getAccountById(accountId);
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        accountRepo.updateAccountBalance(accountId, -amount); // Снимаем деньги с баланса
    }

    // Удаление аккаунта
    @Override
    public void deleteAccount(int accountId) {
        Account account = getAccountById(accountId);
        if (account != null) {
            accountRepo.deleteAccount(accountId); // Удаляем аккаунт через репозиторий
        } else {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }

    @Override
    public void createAccountUSD() {

    }

    @Override
    public void createAccountEUR() {

    }

    @Override
    public void createAccountBTC() {

    }

    @Override
    public Map<Integer, List<Account>> showBalance(int accountID) {
        return Map.of();
    }

    @Override
    public void deleteAccount(int accountID) {

    }

    @Override
    public Map<Integer, List<Account>> myAccounts() {
        return Map.of();

    }
}
