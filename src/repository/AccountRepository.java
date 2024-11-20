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

      return accountList.stream()
              .filter(account -> account.getAccountId() == accountId)
              .map(Account::getBalance)
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException(" Account not found "));
    }

    // Создать аккаунт
    @Override
    public Account createAccount(int userId, CurrencyCode currency, double initialBalance) {

        int accountId = atomicInteger.getAndIncrement();
        Account account = new Account(accountId,currency, initialBalance,userId);
        accountList.add(account);
        List<Account> accountsOfUser = accounts.get(userId);
        if (accountsOfUser == null) {
             accountsOfUser = new ArrayList<>();
        }
        accountsOfUser.add(account);

        return account;
    }

    // Получить аккаунт по ID
    @Override
    public Account getAccountById(int accountId) {
        return  accountList.stream()
                .filter(account -> account.getAccountId() == accountId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(" Account not found "));
    }

    public List<Account> userAccountsByUserId (int userId){

        List<Account> accountsOfUser = accounts.get(userId);
        return accountsOfUser;
}

    // Получить все аккаунты
    @Override
    public List<Account> getAllAccount() {
        return accountList;
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
        accountList.remove(accountId);
        // make it not possible to delete account with non 0 balance
    }

}
