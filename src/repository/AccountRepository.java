package repository;

import models.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepository implements AccountRepoInterface {

    private Map<Integer, Account> accounts = new HashMap<>();

    @Override
    public void createAccount(Account account) {
        if (account != null && !accounts.containsKey(account.getAccountId())) {
            accounts.put(account.getAccountId(), account);
        } else {
            throw new IllegalArgumentException("Account with this ID already exists or account is null.");
        }
    }

    @Override
    public Account getAccountById(int accountId) {
        return accounts.get(accountId);
    }

    @Override
    public List<Account> getAllAccount() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void updateAccountBalance(int accountId, double amount) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
        }
    }

    @Override
    public void deleteAccount(int accountId) {
        accounts.remove(accountId);
    }

    // Очистить все аккаунты
    public void clearAccounts() {
        accounts.clear();
    }
}
