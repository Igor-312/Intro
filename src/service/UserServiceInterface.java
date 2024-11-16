package service;

import models.Role;
import models.User;

import java.util.Map;

public interface UserServiceInterface {

    Map<Integer, User> allUsers();

    void giveAdminPermissions(int userId);

    void blockUser(int userId);

    User findUser(int userId);

    boolean loginUser(String email, String password);

    User registerUser(String email, String password);

    boolean isUserAdmin();

    User getActiveUser();

    void logout();
}
