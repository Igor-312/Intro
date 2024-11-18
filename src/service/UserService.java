package service;

import models.Role;
import models.User;
import repository.UserRepository;
import utils.PersonValidate;
import utils.validatorExeptions.EmailValidateException;
import utils.validatorExeptions.PasswordValidatorException;

import java.util.Map;

public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;  // Используем final, чтобы подчеркнуть неизменяемость
    private User activeUser;
    private static PersonValidate personValidator;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<Integer, User> allUsers() {
        return userRepository.allUsers();
    }

    @Override
    public void giveAdminPermissions(int userId) {
        userRepository.giveAdminPermissions(userId);
    }

    @Override
    public void blockUser(int userId) {
        userRepository.blockUser(userId);
    }

    @Override
    public User findUser(int userId) {
        return userRepository.findUser(userId);
    }

    @Override
    public boolean loginUser(String email, String password) {
        try {
            User user = userRepository.getUserEmail(email);
            if (user == null || !user.getPassword().equals(password)) {
                System.out.println("Invalid email or password.");
                return false;
            }
            activeUser = user;
            System.out.println("User is successfully logged in.");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email or password.");
            return false;
        }
    }


    @Override
    public User registerUser(String email, String password) throws EmailValidateException, PasswordValidatorException {
        if (!personValidator.isEmailValid(email)) {
            System.out.println("Please check the email.");
            return null;
        }
        if (!personValidator.isPasswordValid(password)) {
            System.out.println("Please check the password.");
            return null;
        }
        if (userRepository.isMailExist(email)) {
            System.out.println("Email already exist.");
            return null;
        }
        return userRepository.addUser(email, password);
    }

    @Override
    public boolean isUserAdmin() {
        return activeUser.getRole() == Role.ADMIN;
    }

    @Override
    public boolean isUserBlocked() {
        return activeUser.getRole() == Role.BLOCKED;
    }

    @Override
    public void logout() {
        activeUser = null;
    }

    // Метод для установки activeUser в тестах
    public void setActiveUser(User user) {
        this.activeUser = user;
    }
}
