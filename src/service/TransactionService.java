package service;
import models.Currency;
import models.Transaction;
import models.TypeTransaction;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.TransactionServiceInterface;


import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Map;

public class TransactionService implements TransactionServiceInterface {

    private final TransactionRepository transactionRepository;

    public TransactionService() {
        this.transactionRepository = new TransactionRepository();
    }

    @Override
    public void addMoney(int accountID, double amountOfMoney) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }

        // Создание новой транзакции
        Transaction newTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                amountOfMoney,
                LocalDateTime.now(),
                new Currency("USD", "US Dollar")
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
        AccountRepository accountRepository = new AccountRepository();
        double currentBalance = accountRepository.getAccountBalance(accountID);

        if (currentBalance < amountOfMoney) {
            throw new IllegalArgumentException("Not enough funds on the account.");
        }

        // Создание транзакции на снятие средств
        Transaction withdrawalTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                -amountOfMoney, // Отрицательное значение для снятия
                LocalDateTime.now(),
                new Currency("USD", "Доллар США")
        );

        // Добавление транзакции в репозиторий
        transactionRepository.addTransaction(accountID, withdrawalTransaction);
        System.out.println("Successfully withdrew " + amountOfMoney + " from the account ID: " + accountID);

        // Обновление баланса после транзакции (если требуется)
        accountRepository.updateAccountBalance(accountID, -amountOfMoney);

    }

    // Метод для получения курса обмена (фиктивный)
    private double getExchangeRate(String fromCurrency, String toCurrency) {
        //  обмен USD на EUR
        if ("USD".equals(fromCurrency) && "EUR".equals(toCurrency)) {
            return 0.85; // курс USD к EUR
        }

        if (!"USD".equals(fromCurrency) && !"EUR".equals(toCurrency)) {
            throw new IllegalArgumentException("Unsupported currency exchange: " + fromCurrency + " to " + toCurrency);
        }

        return 1.0; // По умолчанию 1:1 для неподдерживаемых валют
    }

    @Override
    public void exchangeMoney(double amountOfMoney, String currencyFrom, String currencyTo) {

        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }

        double exchangeRate = getExchangeRate(currencyFrom, currencyTo); // Метод для получения курса обмена.

        // Вычисление конвертированной суммы
        double convertedAmount = amountOfMoney * exchangeRate;

        // Создание транзакции обмена валют
        Currency fromCurrency = new Currency(currencyFrom, currencyFrom + " Currency ");
        Currency toCurrency = new Currency(currencyTo, currencyTo + " Currency ");


        Transaction exchangeTransaction = new Transaction(
                generateTransactionId(),
                0, // Специальный ID для обмена валют
                convertedAmount,
                LocalDateTime.now(),
                fromCurrency  // Используем валюту "от"
        );

        // Добавление транзакции в репозиторий
        transactionRepository.addTransaction(0, exchangeTransaction);
        System.out.println("Exchanged " + amountOfMoney + " с " + currencyFrom + " on " + currencyTo + " at the rate " + exchangeRate);
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
                .collect(Collectors.groupingBy(Transaction::getAccountId));
    }

     // AtomicInteger гарантирует, что счетчик будет работать корректно в многозадачных приложениях.
    private static final AtomicInteger transactionIdCounter = new AtomicInteger(0);

    private int generateTransactionId() {
        return transactionIdCounter.incrementAndGet();
    }
}

