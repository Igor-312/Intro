package service;
import models.Currency;
import models.Transaction;
import repository.TransactionRepository;
import service.TransactionServiceInterface;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Map;

public class TransactionService implements TransactionServiceInterface {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void addMoney(int accountID, double amountOfMoney, Transaction transaction) {
        if (amountOfMoney <= 0) {
            System.out.println("The amount of money must be greater than zero.");
            return;
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
            System.out.println("The amount of money must be greater than zero.");
            return;
        }

        // Получаем текущий баланс счета
        double currentBalance = transactionRepository.getAccountBalance(accountID);
        if (currentBalance < amountOfMoney) {
            System.out.println("There are not enough funds in the account.");
            return;
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
    }

    @Override
    public void exchangeMoney(double amountOfMoney, String currencyFrom, String currencyTo) {

        if (amountOfMoney <= 0) {
            System.out.println("The amount of money must be greater than zero.");
            return;
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
                fromCurrency // Используем валюту "от"
        );

        // Добавление транзакции в репозиторий
        transactionRepository.addTransaction(0, exchangeTransaction);
        System.out.println("Exchanged " + amountOfMoney + " с " + currencyFrom + " on " + currencyTo + " at the rate " + exchangeRate);
    }

    // Метод для получения курса обмена (фиктивный)
    private double getExchangeRate(String fromCurrency, String toCurrency) {
        // Пример: для обмена USD на EUR
        if ("USD".equals(fromCurrency) && "EUR".equals(toCurrency)) {
            return 0.85; // курс USD к EUR
        }
        return 1.0; // По умолчанию 1:1 для неподдерживаемых валют
    }

    @Override
    public Map<Integer, List<Transaction>> showHistory() {// Извлечь все транзакции, сгруппированные по account ID
        return transactionRepository.getAllTransactions()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId));
    }

    @Override
    public Map<Integer, List<Transaction>> showUserHistory(int userId) {

        // Извлекаем все транзакции и фильтруем их по userId
        return transactionRepository.getAllTransactions()
                .stream()
                .filter(transaction -> transaction.getUserId() == userId)
                .collect(Collectors.groupingBy(Transaction::getAccountId));
    }

    private int generateTransactionId() {
        return (int) (Math.random() * 100000);
    }
}

