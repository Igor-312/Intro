package service;

import models.Transaction;

import java.util.List;
import java.util.Map;

public class TransactionService implements TransactionServiceInterface{

    @Override
    public void addMoney(int accountID, double amountOfMoney) {

    }

    @Override
    public void withdrawMoney() {

    }

    @Override
    public void exchangeMoney(double amountOfMoney, String currencyFrom, String currencyTo) {

    }

    @Override
    public Map<Integer, List<Transaction>> showHistory() {
        return Map.of();
    }

    @Override
    public Map<Integer, List<Transaction>> showUserHistory(int userId) {
        return Map.of();
    }
}
