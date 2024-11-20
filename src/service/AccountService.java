package service;

import models.Account;


import models.CurrencyCode;
import models.User;
import repository.AccountRepoInterface;
import repository.AccountRepository;
import utils.UserNotFoundException;
import models.Transaction;
import repository.TransactionRepository;


import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.CurrencyCode.*;

public class AccountService implements AccountServiceInterface {


//    private final TransactionService transactionService;
    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
      //  this.transactionService = transactionService; // Передаем через конструктор
        this.accountRepo = accountRepository;

        this.transactionRepository = transactionRepository;
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
       //TODO List<Transaction> transactions = transactionService.showHistory().get(accountId);

        List<Transaction> transactions = transactionRepository.getTransactionsByAccountId(accountId);

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
    public void createAccountUSD(User user) throws UserNotFoundException{

        Account account = accountRepo.createAccount(user.getUserId(),USD, 0.0);
    }

    // Создание аккаунта в EUR
    @Override
    public void createAccountEUR(User user)throws UserNotFoundException {

        Account account = accountRepo.createAccount(user.getUserId(),EUR, 0.0); // Инициализация с балансом 0
    }

    // Создание аккаунта в BTC
    @Override
    public void createAccountBTC(User user) throws UserNotFoundException {

        accountRepo.createAccount(user.getUserId(),BTC,0.0);
    }

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

    @Override
    public List<Account> myAccounts(User user) throws UserNotFoundException {

        return accountRepo.userAccountsByUserId(user.getUserId());

    }
}
