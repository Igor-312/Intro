package repository;

import models.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepositoryImpl implements AccountRepoInterface {

    // Хранилище аккаунтов (Map или List)
    private Map<Integer, Account> accounts = new HashMap<>();

    // Создать аккаунт
    @Override
    public void createAccount(Account account) {
        accounts.put(account.getAccountId(), account); // Добавление в Map
    }

    // Получить аккаунт по ID
    @Override
    public Account getAccountById(int accountId) {
        return accounts.get(accountId); // Доступ к Map по ключу
    }

    // Получить все аккаунты
    @Override
    public List<Account> getAllAccount() {
        return new ArrayList<>(accounts.values()); // Конвертация Map в List
    }

    // Пополнение баланса
    @Override
    public void updateAccountBalance(int accountId, double amount) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setBalance(account.getBalance() + amount); // Обновление баланса
        }
    }

    // Удалить аккаунт
    @Override
    public void deleteAccount(int accountId) {
        accounts.remove(accountId); // Удаление из Map
    }

}
