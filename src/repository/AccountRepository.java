package repository;

import models.Account;
import models.CurrencyCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountRepository implements AccountRepoInterface {

    // Хранилище аккаунтов (Map или List)
    private Map<Integer, List<Account>> accounts = new HashMap<>();//user id -> integer
    private AtomicInteger atomicInteger = new AtomicInteger(1);
    private List<Account> accountList = new ArrayList<>();


    // Возвращает баланс по ID аккаунта
    public double getAccountBalance(int accountId) { // Нужен в TransactionService для метода withdrawMoney чтоб получить тек.баланс счета
        List<Account> account = accounts.get(accountId);
        if (account != null) {
            return account.getBalance();  // Получение баланса у аккаунта
        }
        throw new IllegalArgumentException("Account not found.");
    }

    // Создать аккаунт
    @Override
    public Account createAccount(int userId, CurrencyCode currency, double initialBalance) {

        int accountId = atomicInteger.getAndIncrement();
        Account account = new Account(accountId,currency, initialBalance);
        accountList.add(account);
        accounts.put(userId,accountList);
        return account;
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
    public boolean updateAccountBalance(int accountId, double amount) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            return true;
        }
        return false;
    }

    // Удалить аккаунт
    @Override
    public void deleteAccount(int accountId) {
        accounts.remove(accountId); // Удаление из Map
    }

}
