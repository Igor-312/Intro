package service;
import java.util.Map;
import models.Account;
import models.User;
import utils.UserNotFoundException;

import java.util.List;

public interface AccountServiceInterface {

    void createAccountUSD(User user) throws UserNotFoundException;

    void createAccountEUR(User user) throws UserNotFoundException;

    void createAccountBTC(User user) throws UserNotFoundException;

    Map<Integer, List<Account>> showBalance(int accountID);

    List<Account> myAccounts(User user) throws UserNotFoundException;
  
    // Получение аккаунта по ID
    Account getAccountById(int accountId);

    // Пополнение баланса
    void deposit(int accountId, double amount);

    // Снятие средств
    void withdraw(int accountId, double amount);

    // Удаление аккаунта
    void deleteAccount(int accountId);
}
