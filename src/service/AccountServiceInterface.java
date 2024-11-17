package service;

import models.Account;

import java.util.List;
import java.util.Map;

public interface AccountServiceInterface {

    void createAccountUSD();

    void createAccountEUR();

    void createAccountBTC();

    Map<Integer, List<Account>> showBalance(int accountID);

    void deleteAccount(int accountID);

    Map<Integer, List<Account>> myAccounts();
}
