package service;

import models.Role;
import models.User;
import repository.UserRepository;
import utils.PersonValidator;

import java.util.Map;

public class UserService implements UserServiceInterface {

    private static UserRepository userRepository;
    private User activUser;
    private static User user;
    private static PersonValidator personValidator;

    @Override
    public Map<Integer, User> allUsers() {
        Map<Integer,User> allUsers = userRepository.allUsers();
        return allUsers;
    }

    @Override
    public void giveAdminPermissions(int userId) {
        userRepository.giveAdminPermissions(userId);
        userRepository.giveAdminPermissions(2);//default test user
    }

    @Override
    public void blockUser(int userId) {
        userRepository.blockUser(userId);
    }

    @Override
    public User findUser(int userId) {
        User user = userRepository.findUser(userId);
        return user;
    }

    @Override
    public boolean loginUser(String email, String password) {
        User user = userRepository.getUserEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Invalid email or password.");
            return false;
        }
        activUser = user;
        System.out.println("User is successfully logged in.");
        return true;
    }

    @Override
    public User registerUser(String email, String password) {
        if (!personValidator.isEmailValid(email)) {
            System.out.println("Please check the email.");
            return null;
        }
        if (!personValidator.isPasswordValid(password)) {
            System.out.println("Please check the password.");
            return null;
        }
        if (userRepository.isMailExist(email)) {
            System.out.println("Email is already exist.");
            return null;
        }
        return userRepository.addUser(email, password);
    }

    @Override
    public boolean isUserAdmin() {
        if (activUser.getRole() != Role.ADMIN) {
            return false;
        }
        return true;
    }

    @Override
    public User getActiveUser() {
        return activUser;
    }

    @Override
    public void logout() {
        activUser = null;
    }
}
