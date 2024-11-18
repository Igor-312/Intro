package repository;

import models.Account;
import models.CurrencyCode;

import java.util.List;

public interface AccountRepoInterface {

    // Создание нового аккаунта
    Account createAccount(CurrencyCode currency, double initialBalance);

    // Получение аккаунта по ID
    Account getAccountById(int accountId);

    // Получение всех аккаунтов
    List<Account> getAllAccount();

    // Обновление существующего аккаунта
    void updateAccountBalance(int accountId, double amount);

    // Удаление аккаунта
    void deleteAccount(int accountId);

}
