package repository;

import models.Account;
import models.CurrencyCode;

import javax.security.auth.login.AccountNotFoundException;
import java.util.*;
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
        Account account = new Account(accountId, currency, initialBalance, userId);
        accountList.add(account);
        List<Account> accountsOfUser = accounts.get(userId);
        if (accountsOfUser == null) {
            accountsOfUser = new ArrayList<>();
            // accounts.put(userId, accountsOfUser); // Обновление коллекции accounts
        }
        accountsOfUser.add(account);
        System.out.println("Account id: " + accountId);

        // Отладка: выводим все аккаунты в системе
        // System.out.println("All accounts: " + accountList);
        //System.out.println("Accounts for userId " + userId + ": " + accountsOfUser);

        return account;
    }

    // Получить аккаунт по ID
    @Override
    public Account getAccountById(int accountId) {
        return accountList.stream()
                .filter(account -> account.getAccountId() == accountId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(" Account not found "));
    }

    public List<Account> userAccountsByUserId(int userId) {

        List<Account> accountsOfUser = accountList;

        accountsOfUser.stream()
                .filter(account -> account.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(" Account not found "));

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

        accountList.stream()
                .filter(account -> account.getAccountId() == accountId)
                .filter(account -> account.getBalance() <= 0)
                .findFirst()
                .ifPresentOrElse(
                        account -> accountList.remove(account), // Remove the account if found
                        () -> {
                            throw new IllegalArgumentException("Account not found or only account without money can be deleted");
                        } // Throw exception if no match
                );

        System.out.println("Account with id " + accountId + " deleted ");
    }

    public void save(Account fromAccount) {
    }

    public Account getAccountByCurrency(CurrencyCode currencyFrom) {
        return accountList.stream()
                .filter(account -> account.getCurrency().equals(currencyFrom))
                .findFirst()
                .orElse(null); // если не нашли, вернем null
    }
    }


