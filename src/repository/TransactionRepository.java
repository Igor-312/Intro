package repository;

import models.Account;
import models.Transaction;
import models.TypeTransaction;

import java.util.*;

public class TransactionRepository implements TransactionRepoInterface {

    private final Map<Integer, List<Transaction>> transactionMap = new HashMap<>();
    private final UserRepoInterface userAccountRepo;

    public TransactionRepository(UserRepoInterface userAccountRepo) {
        this.userAccountRepo = userAccountRepo;
    }

    @Override
    public void addTransaction(int accountID, Transaction transaction) {
        if (transaction != null && transaction.getAccountId() == accountID) {
            transactionMap.computeIfAbsent(accountID, k -> new ArrayList<>()).add(transaction);
        } else {
            System.out.println("Cannot add a null transaction or mismatched accountID.");
        }
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        return transactionMap.getOrDefault(accountId, new ArrayList<>());
    }

    @Override
    public List<Transaction> getTransactionsByType(Enum type) {

        List<Transaction> filteredTransactions = new ArrayList<>();
        if (type instanceof TypeTransaction) {
            for (List<Transaction> transactions : transactionMap.values()) {
                for (Transaction transaction : transactions) {
                    if (transaction.getType().equals(type)) {
                        filteredTransactions.add(transaction);
                    }
                }
            }
        }
        return filteredTransactions;
    }


    @Override
    public Map<Integer, List<Transaction>> getTransactionsByUserId(int userId) {
        Map<Integer, List<Transaction>> userTransactions = new HashMap<>();
        List<Account> userAccounts = userAccountRepo.getAccountsByUserId(userId);

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
        double balance = 0.0;
        List<Transaction> transactions = getTransactionsByAccountId(accountID);
        for (Transaction transaction : transactions) {
            //  вычитание для снятия, добавление для депозита
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
