package service;

import models.Account;
import models.User;
import models.User.Role;
import repository.UserRepoInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService implements UserRepoInterface {

    private final Map<Integer, User> users;
    private int userIdCounter;

    public UserService() {
        this.users = new HashMap<>();
        this.userIdCounter = 1;
    }

    @Override
    public void addDefaultUsers() {
        users.put(1, new User("Masha123@gmail.com", "Masha123@gmail.com", userIdCounter++));
        users.put(2, new User("Neshyna123@gmail.com", "Neshyna123@gmail.com", userIdCounter++));
    }

    @Override
    public User addUser(String email, String password) {
        if (isMailExist(email)) {
            throw new IllegalArgumentException("Email already exists.");
        }
        User newUser = new User(email, password, userIdCounter++);
        users.put(newUser.getUserId(), newUser);
        return newUser;
    }

    @Override
    public boolean isMailExist(String email) {
        return users.values().stream()
                .map(User::getEmail)
                .anyMatch(existingEmail -> existingEmail.equals(email));
    }

    @Override
    public User getUserEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Email not found."));
    }

    @Override
    public void giveAdminPermissions(int userId) {
        users.values().stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .ifPresentOrElse(
                        user -> {
                            if (user.getRole() != Role.ADMIN) {
                                user.setRole(Role.ADMIN);
                            } else {
                                System.out.println("User is already Admin");
                            }
                        },
                        () -> {throw new IllegalArgumentException("User not found");}
                );
    }

    @Override
    public void blockUser(int userId) {
        users.values().stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .ifPresentOrElse(
                        user -> user.setRole(Role.BLOCKED),
                        () -> {throw new IllegalArgumentException("User not found");}
                );
    }

    @Override
    public User findUser(int userId) {
        return users.values().stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    @Override
    public Map<Integer, User> allUsers() {
        return new HashMap<>(users);
    }

    @Override
    public List<Account> getAccountsByUserId(int userId) {
        return users.values().stream()
                .filter(user -> user.getUserId() == userId)
                .map(User::getUserAccounts)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public User loginUser(String email, String password) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }
}
