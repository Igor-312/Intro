package repository;

import models.Account;

import java.util.List;

public interface UserRepoInterface {


    List<Account> getAccountsByUserId(int userId);
}

