package service;

import models.Currency;
import models.CurrencyCode;
import models.Transaction;
import models.TypeTransaction;
import repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }

        Transaction newTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                amountOfMoney,
                LocalDateTime.now(),
                new Currency(CurrencyCode.USD, "US Dollar"),
                TypeTransaction.CREDIT
        );

        transactionRepository.addTransaction(accountID, newTransaction);
    }

    @Override
    public void withdrawMoney(int accountID, double amountOfMoney) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }

        Transaction withdrawalTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                -amountOfMoney,
                LocalDateTime.now(),
                new Currency(CurrencyCode.USD, "US Dollar"),
                TypeTransaction.DEBIT
        );

        transactionRepository.addTransaction(accountID, withdrawalTransaction);
    }

    @Override
    public void exchangeMoney(double amountOfMoney, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }

        double exchangeRate = getExchangeRate(currencyFrom.name(), currencyTo.name());
        double convertedAmount = amountOfMoney * exchangeRate;

        Transaction exchangeTransaction = new Transaction(
                generateTransactionId(),
                0,
                convertedAmount,
                LocalDateTime.now(),
                new Currency(currencyTo, currencyTo + " Currency"),
                TypeTransaction.EXCHANGE
        );

        transactionRepository.addTransaction(0, exchangeTransaction);
    }

    @Override
    public Map<Integer, List<Transaction>> showHistory() {
        return transactionRepository.getAllTransactions()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId));
    }

    @Override
    public Map<Integer, List<Transaction>> showUserHistory(int userId) {
        List<Integer> userAccounts = userService.getAccountsByUserId(userId)
                .stream()
                .map(account -> account.getAccountId())
                .collect(Collectors.toList());

        return transactionRepository.getAllTransactions()
                .stream()
                .filter(transaction -> userAccounts.contains(transaction.getAccountId()))
                .collect(Collectors.groupingBy(Transaction::getAccountId));
    }

    private int generateTransactionId() {
        return (int) (Math.random() * 100000);
    }

    private double getExchangeRate(String fromCurrency, String toCurrency) {
        if ("USD".equals(fromCurrency) && "EUR".equals(toCurrency)) {
            return 0.85;
        }
        return 1.0;
    }
}
