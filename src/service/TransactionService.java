package service;
import models.Account;
import models.Currency;
import models.CurrencyCode;
import models.Transaction;
import repository.AccountRepository;
import repository.TransactionRepository;


import java.time.LocalDateTime;

import java.util.concurrent.atomic.AtomicInteger;

import java.util.stream.Collectors;

import java.util.List;
import java.util.Map;

import static models.CurrencyCode.USD;

public class TransactionService implements TransactionServiceInterface {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final CurrencyService currencyService;
    private final UserService userService;

   public TransactionService(AccountService accountService,
                             TransactionRepository transactionRepository,
                             AccountRepository accountRepository,
                             CurrencyService currencyService, UserService userService) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.currencyService = currencyService;
        this.userService = userService;
   }

    @Override
    public void addMoney(int accountID, double amountOfMoney) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }

        // Получение аккаунта через AccountService
        Account account = accountService.getAccountById(accountID);
        if (account == null) {
            throw new IllegalArgumentException("Account not found with ID: " + accountID);
        }

        // Обновление баланса аккаунта
        accountService.deposit(accountID, amountOfMoney);

        int userId = account.getUserId();

        // Создание новой транзакции
        Transaction newTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                amountOfMoney,
                LocalDateTime.now(),
                new Currency(account.getCurrency()), // Генерация объекта Currency
                userId
                );

        // Добавление транзакции в репозиторий
        transactionRepository.addTransaction(accountID, newTransaction);

        System.out.println("Successfully added " + amountOfMoney + " on account ID: " + accountID);
    }

    @Override
    public void withdrawMoney(int accountID, double amountOfMoney) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }
        // Получаем текущий баланс счета
        double currentBalance = accountRepository.getAccountBalance(accountID);
        if (currentBalance < amountOfMoney) {
            throw new IllegalArgumentException("Not enough funds on the account.");
        }

        // Обновление баланса после транзакции (если требуется)
        accountRepository.updateAccountBalance(accountID, -amountOfMoney);

        int userId = userService.getActiveUser().getUserId();

        // Создание транзакции на снятие средств
        Transaction withdrawalTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                -amountOfMoney, // Отрицательное значение для снятия
                LocalDateTime.now(),
                new Currency(USD),
                userId
        );

        // Добавление транзакции в репозиторий
        transactionRepository.addTransaction(accountID, withdrawalTransaction);
        System.out.println("Successfully withdrew " + amountOfMoney + " from the account ID: " + accountID);
    }

    // Метод для получения курса обмена
    private double getExchangeRate(CurrencyCode fromCurrency, CurrencyCode toCurrency) {
        double fromRate = currencyService.getExchangeRate(fromCurrency.name());
        double toRate = currencyService.getExchangeRate(toCurrency.name());

        if (fromRate <= 0 || toRate <= 0) {
            throw new IllegalArgumentException("Invalid or unsupported currency rates for: " + fromCurrency + " to " + toCurrency);
        }

        return toRate / fromRate; // Курс обмена между валютами
    }

    @Override
    public void exchangeMoney(double amountOfMoney, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }

        int userId = userService.getActiveUser().getUserId();

        // Получение курса обмена
        double exchangeRate = getExchangeRate(currencyFrom, currencyTo);

        // Вычисление конвертированной суммы
        double convertedAmount = amountOfMoney * exchangeRate;

        // Создание объекта Currency для результата
        Currency resultingCurrency = new Currency(currencyTo);

        // Создание транзакции
        Transaction exchangeTransaction = new Transaction(
                generateTransactionId(),
                0, // Специальный ID для транзакций обмена валют
                convertedAmount,
                LocalDateTime.now(),
                resultingCurrency,
                userId
        );

        // Добавление транзакции в репозиторий
        transactionRepository.addTransaction(0, exchangeTransaction);

        System.out.println("Converted " + amountOfMoney + " from " + currencyFrom + " to " + currencyTo + " with the rate " + exchangeRate);
    }


    @Override
    public Map<Integer, List<Transaction>> showHistory() {// Извлечь все транзакции, сгруппированные по account ID
        return transactionRepository.getAllTransactions()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId));
    }

    @Override
    public Map<Integer, List<Transaction>> showUserHistory(int userId) {
       return transactionRepository.getAllTransactions()
               .stream()
               .filter(transaction -> transaction.getUserId() == userId)
               .collect(Collectors.groupingBy(Transaction::getTransactionId));
    }

     // AtomicInteger гарантирует, что счетчик будет работать корректно в многозадачных приложениях.
    private static final AtomicInteger transactionIdCounter = new AtomicInteger(0);

    private int generateTransactionId() {
        return transactionIdCounter.incrementAndGet();
    }
}

