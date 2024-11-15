package service;

import models.Transaction;

import java.util.List;
import java.util.Map;

public class TransactionService {
    public static void addMoney(int accountID, double amountOfMoney) {
    }

    public static void withdrawMoney() {
    }

    public static void exchangeMoney(double amountOfMoney, String currencyFrom, String currencyTo) {
    }

    public static Map<Integer, List<Transaction>> showHistory() {

        return null;
    }

    public static Map<Integer, List<Transaction>> showUserHistory(int userId) {
        return Map.of();
    }
}
