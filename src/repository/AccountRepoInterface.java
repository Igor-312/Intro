package repository;

import models.Account;
import models.CurrencyCode;

import java.util.List;
import java.util.Map;

public interface AccountRepoInterface {
    Account createAccountForUser(int userId, CurrencyCode currency, double initialBalance);
    Account getAccountById(int accountId);
    double getAccountBalance(int accountId);
    void updateAccountBalance(int accountId, double amount);
    void deleteAccountById(int accountId);
    Map<Integer, List<Account>> getAccountsByUserId(int userId);
    Map<Integer, List<Account>> getAllAccounts();
    void clearAccounts(); // Для тестов
}
