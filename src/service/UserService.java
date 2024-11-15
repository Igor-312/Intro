package service;

import models.User;
import repository.UserRepository;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean loginUser(String email2, String password1) {
        return false;
    }

    public User registerUser(String email, String password) {
        return null;
    }

    public boolean isUserAdmin() {
        return false;
    }
}
