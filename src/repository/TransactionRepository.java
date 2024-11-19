package repository;

import models.Account;
import models.Transaction;
import models.TypeTransaction;
import service.UserService;

import java.util.*;

public class TransactionRepository implements TransactionRepoInterface {

    private final Map<Integer, List<Transaction>> transactionMap = new HashMap<>();
    private UserService userService;

    public TransactionRepository() {
        this.userService = userService;
    }

    @Override
    public void addTransaction(int accountID, Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }
        if (transaction.getAccountId() != accountID) {
            throw new IllegalArgumentException("Account ID in transaction does not match the provided account ID.");
        }
        if (!transactionMap.containsKey(accountID)) {
            throw new IllegalArgumentException("Account ID not found in the transaction repository.");
        }

        transactionMap.computeIfAbsent(accountID, k -> new ArrayList<>()).add(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        if (!transactionMap.containsKey(accountId)) {
            throw new IllegalArgumentException("Account ID not found in the transaction repository.");
        }
        return transactionMap.get(accountId);
    }

   @Override
    public List<Transaction> getTransactionsByType(Enum type) {

        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null.");
        }

        List<Transaction> filteredTransactions = new ArrayList<>();
        if (type instanceof TypeTransaction) {
            for (List<Transaction> transactions : transactionMap.values()) {
                for (Transaction transaction : transactions) {
                    if (transaction.getType().equals(type)) {
                        filteredTransactions.add(transaction);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid transaction type provided.");
        }
        return filteredTransactions;
    }


    @Override
    public Map<Integer, List<Transaction>> getTransactionsByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than zero.");
        }

        Map<Integer, List<Transaction>> userTransactions = new HashMap<>();
        List<Account> userAccounts = userService.getAccountsByUserId(userId);

        if (userAccounts.isEmpty()) {
            throw new IllegalArgumentException("No accounts found for the provided user ID.");
        }

        for (Account account : userAccounts) {
            int accountId = account.getAccountId();
            if (transactionMap.containsKey(accountId)) {
                userTransactions.put(accountId, new ArrayList<>(transactionMap.get(accountId)));
            }
        }

        return userTransactions;
    }

    @Override
    public double getAccountBalance(int accountID) { // текущий баланс счета. реализация в TransactionService.
        if (!transactionMap.containsKey(accountID)) {
            throw new IllegalArgumentException("Account ID not found in the transaction repository.");
        }

        double balance = 0.0;
        List<Transaction> transactions = getTransactionsByAccountId(accountID);
        for (Transaction transaction : transactions) {
            // вычитание для снятия, добавление для депозита
            if (transaction.getType() == TypeTransaction.CREDIT) {
                balance += transaction.getAmount();
            } else if (transaction.getType() == TypeTransaction.DEBIT) {
                balance -= transaction.getAmount();
            }
        }
        return balance;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        for (List<Transaction> transactions : transactionMap.values()) {
            allTransactions.addAll(transactions);
        }
        return allTransactions;


    }
}
