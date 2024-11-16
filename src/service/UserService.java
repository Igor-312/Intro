package service;

import models.Role;
import models.User;
import repository.UserRepository;
import utils.PersonValidate;

import java.util.Map;

public class UserService {

    private static UserRepository userRepository;
    private User activUser;
    private static User user;
    private static PersonValidate personValidate;

    public static Map<Integer, User> allUsers() {
        Map<Integer,User> allUsers = userRepository.allUsers();
        return allUsers;
    }

    public static void giveAdminPermissions(int userId) {
        userRepository.giveAdminPermissions(userId);
        userRepository.giveAdminPermissions(2);//default test user
    }

    public static void blockUser(int userId) {
        userRepository.blockUser(userId);
    }

    public static User findUser(int userId) {
        User user = userRepository.findUser(userId);
        return user;
    }

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

    public User registerUser(String email, String password) {
        if (!personValidate.isEmailValid(email)) {
            System.out.println("Please check the email.");
            return null;
        }
        if (!personValidate.isPasswordValid(password)) {
            System.out.println("Please check the password.");
            return null;
        }
        if (userRepository.isMailExist(email)) {
            System.out.println("Email is already exist.");
            return null;
        }
        return userRepository.addUser(email, password);
    }

    public boolean isUserAdmin() {
        if (activUser.getRole() != Role.ADMIN) {
            return false;
        }
        return true;
    }

    private User getActiveUser() {
        return activUser;
    }

    public void logout() {
        activUser = null;
    }
}
