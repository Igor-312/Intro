package repository;

import models.Account;

import java.util.List;

public interface AccountRepoInterface {

    // Создание нового аккаунта
    void createAccount(Account account);

    // Получение аккаунта по ID
    Account getAccountById(int accountId);

    // Получение всех аккаунтов
    List<Account> getAllAccount();

    // метод, который обновляет баланс конкретного аккаунта. Нужен в TransactionService для метода withdrawMoney
    public boolean updateAccountBalance(int accountId, double amount);

    // Удаление аккаунта
    void deleteAccount(int accountId);

}
