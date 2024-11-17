package service;

import models.Account;

import java.util.List;
import java.util.Map;

public class AccountService implements AccountServiceInterface{

    @Override
    public void createAccountUSD() {

    }

    @Override
    public void createAccountEUR() {

    }

    @Override
    public void createAccountBTC() {

    }

    @Override
    public Map<Integer, List<Account>> showBalance(int accountID) {
        return Map.of();
    }

    @Override
    public void deleteAccount(int accountID) {

    }

    @Override
    public Map<Integer, List<Account>> myAccounts() {
        return Map.of();
    }
}
