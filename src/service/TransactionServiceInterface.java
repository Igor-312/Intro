package service;

import models.CurrencyCode;
import models.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionServiceInterface {

    void addMoney(int accountID, double amountOfMoney);

    void withdrawMoney(int accountID, double amountOfMoney);

    void exchangeMoney(double amountOfMoney, CurrencyCode currencyFrom, CurrencyCode currencyTo);

    Map<Integer, List<Transaction>> showHistory();

    Map<Integer, List<Transaction>> showUserHistory(int userId);

}
