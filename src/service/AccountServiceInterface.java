package service;

import models.Account;

import java.util.List;

public interface AccountServiceInterface {

    // Создание нового аккаунта
    void createAccount(Account account);

    // Получение аккаунта по ID
    Account getAccountById(int accountId);

    // Получение всех аккаунтов
    List<Account> getAllAccounts();

    // Пополнение баланса
    void deposit(int accountId, double amount);

    // Снятие средств
    void withdraw(int accountId, double amount);

    // Удаление аккаунта
    void deleteAccount(int accountId);

}
