package repository;

import models.Transaction;
import models.TypeTransaction;
import java.util.List;
import java.util.Map;

public interface TransactionRepoInterface {
    void addTransaction(int accountID, Transaction transaction);
    List<Transaction> getTransactionsByAccountId(int accountId);
    List<Transaction> getTransactionsByType(Enum type);
    double getAccountBalance(int accountID);
    List<Transaction> getAllTransactions();
    Map<Integer, List<Transaction>> getTransactionsByUserId(int userId);
}
