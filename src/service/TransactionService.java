package service;

import models.Transaction;
import models.TypeTransaction;
import repository.TransactionRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Map;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // Другие методы...

    public void withdrawMoney(int accountID, double amountOfMoney) {
        System.out.println("Withdrawing money");
        // Примерный код для снятия денег
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        double currentBalance = transactionRepository.getAccountBalance(accountID);
        if (currentBalance < amountOfMoney) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        Transaction withdrawalTransaction = new Transaction(accountID, amountOfMoney, TypeTransaction.DEBIT, LocalDateTime.now());
        transactionRepository.addTransaction(accountID, withdrawalTransaction);
    }
}
