package service;

import models.Currency;
import models.Transaction;
import repository.TransactionRepository;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionService implements TransactionServiceInterface {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    @Override
    public void addMoney(int accountID, double amountOfMoney) {
        if (amountOfMoney <= 0) {
            System.out.println("The amount of money must be greater than zero.");
            return;
        }

        Transaction newTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                amountOfMoney,
                LocalDateTime.now(),
                new Currency("USD", "US Dollar")
        );

        transactionRepository.addTransaction(accountID, newTransaction);
        System.out.println("Successfully added " + amountOfMoney + " on account ID: " + accountID);
    }

    @Override
    public void withdrawMoney(int accountID, double amountOfMoney) {
        if (amountOfMoney <= 0) {
            System.out.println("The amount of money must be greater than zero.");
            return;
        }

        double currentBalance = transactionRepository.getAccountBalance(accountID);
        if (currentBalance < amountOfMoney) {
            System.out.println("There are not enough funds in the account.");
            return;
        }

        Transaction withdrawalTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                -amountOfMoney,
                LocalDateTime.now(),
                new Currency("USD", "US Dollar")
        );

        transactionRepository.addTransaction(accountID, withdrawalTransaction);
        System.out.println("Successfully withdrew " + amountOfMoney + " from the account ID: " + accountID);
    }

    @Override
    public void exchangeMoney(double amountOfMoney, String currencyFrom, String currencyTo) {
        if (amountOfMoney <= 0) {
            System.out.println("The amount of money must be greater than zero.");
            return;
        }

        double exchangeRate = getExchangeRate(currencyFrom, currencyTo);
        double convertedAmount = amountOfMoney * exchangeRate;

        Currency fromCurrency = new Currency(currencyFrom, currencyFrom + " Currency");
        Currency toCurrency = new Currency(currencyTo, currencyTo + " Currency");

        Transaction exchangeTransaction = new Transaction(
                generateTransactionId(),
                0,
                convertedAmount,
                LocalDateTime.now(),
                fromCurrency
        );

        transactionRepository.addTransaction(0, exchangeTransaction);
        System.out.println("Exchanged " + amountOfMoney + " from " + currencyFrom + " to " + currencyTo + " at the rate " + exchangeRate);
    }

    private double getExchangeRate(String fromCurrency, String toCurrency) {
        if ("USD".equals(fromCurrency) && "EUR".equals(toCurrency)) {
            return 0.85;
        }
        return 1.0;
    }

    @Override
    public Map<Integer, List<Transaction>> showHistory() {
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

    private int generateTransactionId() {
        return (int) (Math.random() * 100000);
    }
}
