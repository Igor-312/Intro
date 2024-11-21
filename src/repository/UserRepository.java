package repository;

import models.*;
import utils.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository implements UserRepoInterface{
    public static Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger atomicInteger = new AtomicInteger(1);


    public UserRepository() {
        addDefaultUsers();
        addTestAdmin();

    }

    public void addDefaultUsers(){
        users.put(111, new User("Masha123@gmail.com", "Masha123@gmail.com",88));
    }

    public void addTestAdmin(){
        User admin = new User("Neshyna123@gmail.com", "Neshyna123@gmail.com",99);
        admin.setRole(Role.ADMIN);
        users.put(222,admin);
    }


    public User addUser(String email, String password){

        if (isMailExist(email)) {
            throw new IllegalArgumentException("Email already exists.");
        }
        int userId = atomicInteger.getAndIncrement();
        User newUser = new User(email,password,userId);
        users.put(atomicInteger.getAndIncrement(),newUser);
        return newUser;
    }

    public boolean isMailExist(String email) {
       return users.values().stream()
                .map(User::getEmail)
                .anyMatch(existEmail -> existEmail.equals(email));
    }

    public User getUserEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Email not found."));
    }

    public void giveAdminPermissions(int userId){

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

    public void blockUser(int userId){
        users.values().stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .ifPresentOrElse(
                        user -> user.setRole(Role.BLOCKED),
                        () -> {throw new IllegalArgumentException("User not found");}
                );
    }

    public User findUser(int userId) {
        return users.values().stream()
                .filter(user -> user.getUserId() == (userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public Map<Integer, User> allUsers() {
        return users;
    }

    public List<Account> getAccountsByUserId(int userId) {
        return List.of();
    }
}
