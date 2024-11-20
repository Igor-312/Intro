package repository;

import models.CurrencyCode;
import models.Account;
import models.User; // Добавляем импорт класса User

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountRepository implements AccountRepoInterface {

    private final Map<Integer, Account> accounts = new HashMap<>();
    private int accountCounter = 1;

    @Override
    public Account createAccountForUser(int userId, CurrencyCode currency, double initialBalance) {
        Account account = new Account(currency, initialBalance);
        account.setAccountId(accountCounter++);
        // Вместо создания нового пользователя, используем существующего
        User user = findUserById(userId);
        account.setUser(user);
        accounts.put(account.getAccountId(), account);
        return account;
    }

    @Override
    public Account getAccountById(int accountId) {
        return accounts.get(accountId);
    }

    @Override
    public double getAccountBalance(int accountID) {
        Account account = accounts.get(accountID);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        return account.getBalance();
    }

    @Override
    public void updateAccountBalance(int accountID, double amount) {
        Account account = accounts.get(accountID);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void deleteAccountById(int accountId) {
        accounts.remove(accountId);
    }

    @Override
    public Map<Integer, List<Account>> getAccountsByUserId(int userId) {
        return accounts.values().stream()
                .filter(account -> account.getUser().getUserId() == userId)
                .collect(Collectors.groupingBy(account -> account.getUser().getUserId()));
    }

    @Override
    public Map<Integer, List<Account>> getAllAccounts() {
        return accounts.values().stream()
                .collect(Collectors.groupingBy(account -> account.getUser().getUserId()));
    }

    @Override
    public void clearAccounts() {
        accounts.clear();
        accountCounter = 1;
    }

    private User findUserById(int userId) {
        // Ваш метод для поиска пользователя по ID
        return UserRepository.users.get(userId);
    }
}
