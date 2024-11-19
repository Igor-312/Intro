package service;
import java.util.Map;
import models.Account;

import java.util.List;

public interface AccountServiceInterface {

    void createAccountUSD();

    void createAccountEUR();

    void createAccountBTC();

    Map<Integer, List<Account>> showBalance(int accountID);

    Map<Integer, List<Account>> myAccounts();
  
    // Получение аккаунта по ID
    Account getAccountById(int accountId);

    // Пополнение баланса
    void deposit(int accountId, double amount);

    // Снятие средств
    void withdraw(int accountId, double amount);

    Map<String, Object> getAccountDetails(int accountId);

    // Удаление аккаунта
    void deleteAccount(int accountId);
}
