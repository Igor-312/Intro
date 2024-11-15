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

    // Обновление существующего аккаунта
    void updateAccount(Account account);

    // Удаление аккаунта
    void deleteAccount(int accountId);

}
