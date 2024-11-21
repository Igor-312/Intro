package repository;

import models.Account;
import models.Transaction;
import models.TypeTransaction;
import service.AccountService;
import service.UserService;

import java.util.*;

public class TransactionRepository implements TransactionRepoInterface {

    private final Map<Integer, List<Transaction>> transactionMap = new HashMap<>();
    private AccountService accountService;

    @Override
    public void addTransaction(int accountID, Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }
        if (transaction.getAccountId() != accountID) {
            throw new IllegalArgumentException("Account ID in transaction does not match the provided account ID.");
        }
        // Проверяем существование счета в системе (через UserService)
//        if (!accountService(accountID)) { // Метод doesAccountExist   реализован в UserService
//            throw new IllegalArgumentException("Account ID does not exist in the system.");
//        }
        // Проверка существования счета в transactionMap
        if (!transactionMap.containsKey(accountID)) {
            transactionMap.put(accountID, new ArrayList<>()); // Если счета нет в repository, создаем его
        }
        // Добавление транзакции в хранилище
        transactionMap.get(accountID).add(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        if (!transactionMap.containsKey(accountId)) {
            throw new IllegalArgumentException("Account ID not found in the transaction repository.");
        }
        return transactionMap.get(accountId);
    }

    /*@Override
    public List<Transaction> getTransactionsByType(Enum type) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null.");
        }

        List<Transaction> filteredTransactions = new ArrayList<>();
            for (List<Transaction> transactions : transactionMap.values()) {
                for (Transaction transaction : transactions) {
                    if (transaction.getType().equals(type)) {
                        filteredTransactions.add(transaction);
                    }
                }

        }
        return filteredTransactions;
    }*/


   /* @Override
    public Map<Integer, List<Transaction>> getTransactionsByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than zero.");
        }

        Map<Integer, List<Transaction>> userTransactions = new HashMap<>();
        List<Account> userAccounts = accountService.listOfUserAccountsByUserId(userId);

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
    }*/

   /* @Override
    public double getAccountBalance(int accountID) { // текущий баланс счета. реализация в TransactionService.
        if (!transactionMap.containsKey(accountID)) {
            throw new IllegalArgumentException("Account ID not found in the transaction repository.");
        }

        double balance = 0.0;
        List<Transaction> transactions = getTransactionsByAccountId(accountID);

        for (Transaction transaction : transactions) {
            // Проверка на отрицательную сумму для кредитных транзакций
            if (transaction.getAmount() <= 0) {
                throw new IllegalArgumentException("Transaction amount must be greater than zero.");
            }
            // Обработка кредитных транзакций (добавление суммы)
            if (transaction.getType() == TypeTransaction.CREDIT) {
                balance += transaction.getAmount();
            }
            // Обработка дебетовых транзакций (вычитание суммы)
            else if (transaction.getType() == TypeTransaction.DEBIT) {
                if (balance < transaction.getAmount()) {
                    throw new IllegalArgumentException("Insufficient funds for the debit transaction.");
                }
                balance -= transaction.getAmount();
            }
            // Обработка всех других типов транзакций
            else {
                throw new IllegalArgumentException("Unknown transaction type: " + transaction.getType());
            }
        }
        return balance;
    }*/

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        for (List<Transaction> transactions : transactionMap.values()) {
            allTransactions.addAll(transactions);
        }
        return allTransactions;
    }


    public void save(Transaction exchangeTransaction) {

    }
}
