package repository;

import models.Account;

import java.util.List;

import models.Role;
import models.User;

import java.util.Map;

public interface UserRepoInterface {
    void addDefaultUsers();

    User addUser(String email, String password);

    boolean isMailExist(String email);

    User getUserEmail(String email);

    void giveAdminPermissions(int userId);

    void blockUser(int userId);

    User findUser(int userId);

    Map<Integer, User> allUsers();
  
    List<Account> getAccountsByUserId(int userId);
}

