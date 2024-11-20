package service;

import models.Account;
import models.Role;
import models.User;
import models.CurrencyCode;
import repository.UserRepository;
import utils.validatorExeptions.EmailValidateException;
import utils.validatorExeptions.PasswordValidatorException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService {

    private UserRepository userRepository;
    private User activeUser;

    // Конструктор с параметром
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Конструктор по умолчанию
    public UserService() {
        this.userRepository = new UserRepository();
    }

    public Map<Integer, User> getAllUsers() {
        return userRepository.allUsers();
    }

    public void addUser(String email, String password) throws EmailValidateException, PasswordValidatorException {
        userRepository.addUser(email, password);
    }

    public User getActiveUser() {
        return activeUser;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.getUserEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            activeUser = user;
            return user;
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }

    public boolean isUserBlocked() {
        return activeUser != null && activeUser.getRole() == Role.BLOCKED;
    }

    public Optional<User> registerUser(String email, String password) throws EmailValidateException, PasswordValidatorException {
        User user = new User(email, password);
        addUser(email, password);
        return Optional.of(user);
    }

    public void giveAdminPermissions(int userId) {
        User user = userRepository.findUser(userId);
        if (user != null) {
            user.setRole(Role.ADMIN);
        }
    }

    public void blockUser(int userId) {
        User user = userRepository.findUser(userId);
        if (user != null) {
            user.setRole(Role.BLOCKED);
        }
    }

    public User findUser(int userId) {
        return userRepository.findUser(userId);
    }

    public boolean isUserAdmin() {
        return activeUser != null && activeUser.getRole() == Role.ADMIN;
    }

    public List<Account> getAccountsByUserId(int userId) {
        return userRepository.getAccountsByUserId(userId);
    }

    // Добавляем метод createAccountForUser
    public Account createAccountForUser(int userId, CurrencyCode currency, double initialBalance) {
        return userRepository.createAccountForUser(userId, currency, initialBalance);
    }

    // Добавляем метод allUsers
    public Map<Integer, User> allUsers() {
        return userRepository.allUsers();
    }

    // Добавляем метод logout
    public void logout() {
        activeUser = null;
    }
}

