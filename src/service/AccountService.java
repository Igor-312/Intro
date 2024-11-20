package service;

import models.Account;

import models.Transaction;
import repository.AccountRepoInterface;
import repository.AccountRepository;
import repository.TransactionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.CurrencyCode.*;

public class AccountService implements AccountServiceInterface {

    private final TransactionService transactionService;
    private final AccountRepoInterface accountRepo;

    public AccountService(TransactionService transactionService, AccountRepoInterface accountRepository) {
        this.transactionService = transactionService; // Передаем через конструктор
        this.accountRepo = accountRepository;
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
        accountRepo.updateAccountBalance(accountId, amount); // Пополнение счета
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
        accountRepo.updateAccountBalance(accountId, -amount); // Снятие средств с баланса
    }

    @Override
    public Map<String, Object> getAccountDetails(int accountId) {
        Account account = getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }

        // Получение кода валюты через CurrencyCode
        String currencyCode = account.getCurrency().name();  // Используем name() для перечисления CurrencyCode

        // Получение транзакций через TransactionService
        List<Transaction> transactions = transactionService.showHistory().get(accountId);

        // Подготовка результата для отображения
        Map<String, Object> accountDetails = new HashMap<>();
        accountDetails.put("Account ID", accountId);
        accountDetails.put("Currency", currencyCode);  // Используем код валюты
        accountDetails.put("Balance", account.getBalance());
        accountDetails.put("Transactions", transactions != null ? transactions : List.of());

        return accountDetails;
    }

    // Удаление аккаунта
    @Override
    public void deleteAccount(int accountId) {
        Account account = getAccountById(accountId);
        if (account != null) {
            accountRepo.deleteAccount(accountId); // Удаление аккаунта
        } else {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }
    }

    // Создание аккаунта в USD
    @Override
    public void createAccountUSD() {
        Account account = accountRepo.createAccount(USD, 0.0);
    }

    // Создание аккаунта в EUR
    @Override
    public void createAccountEUR() {
        Account account = accountRepo.createAccount(EUR, 0.0); // Инициализация с балансом 0
    }

    // Создание аккаунта в BTC
    @Override
    public void createAccountBTC() {
        Account account = accountRepo.createAccount(BTC, 0.0);
    }

    // Метод для генерации уникального ID аккаунта
    private static int accountIdCounter = 1; // Статический счетчик для уникальных ID

    // Показать баланс для аккаунта
    @Override
    public Map<Integer, List<Account>> showBalance(int accountId) {
        Map<Integer, List<Account>> result = new HashMap<>();
        Account account = getAccountById(accountId);
        if (account != null) {
            result.put(accountId, List.of(account)); // Возвращаем только один аккаунт
        }
        return result;
    }

    // Показать все аккаунты
    @Override
    public Map<Integer, List<Account>> myAccounts() {
        Map<Integer, List<Account>> allAccounts = new HashMap<>();
        List<Account> accounts = accountRepo.getAllAccount();
        allAccounts.put(0, accounts);
        return allAccounts;
    }
}
