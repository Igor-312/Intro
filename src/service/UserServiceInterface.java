package service;

import models.Account;
import models.Role;
import models.User;
import utils.validatorExeptions.EmailValidateException;
import utils.validatorExeptions.PasswordValidatorException;

import java.util.List;
import java.util.Map;

public interface UserServiceInterface {

    Map<Integer, User> allUsers();

    void giveAdminPermissions(int userId);

    void blockUser(int userId);

    User findUser(int userId);

    boolean loginUser(String email, String password);

    User registerUser(String email, String password) throws EmailValidateException, PasswordValidatorException;

    boolean isUserAdmin();

    boolean isUserBlocked();

    void logout();

    List<Account> getAccountsByUserId(int userId);
}
