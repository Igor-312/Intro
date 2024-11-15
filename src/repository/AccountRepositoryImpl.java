package repository;

import models.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepositoryImpl implements AccountRepoInterface {
    private Map<Integer, Account> accounts = new HashMap<>();


    @Override
    public void createAccount(Account account) {
        account.add(account);
    }

    @Override
    public Account getAccountById(int accountId) {
        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }

        return null;
    }

    @Override
    public List<Account> getAllAccount() {
        return List.of();
    }

    @Override
    public void updateAccount(Account account) {
        // Поиск и обновление логики
        Account existingAccount = getAccountById(account.getAccountId());
        if (existingAccount != null) {
            existingAccount.setCurrency(account.getCurrency());
            existingAccount.setBalance(account.getBalance());
            existingAccount.setUser(account.getUser());
        }
    }

    @Override
    public void deleteAccount(int accountId) {
        accounts.removeIf(account -> account.getAccountId() == accountId);
    }
}
