package repository;

import models.Role;
import models.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository implements UserRepoInterface{
    private static Map<Integer, User> users = new HashMap<>();
    private static int userIdCounter = 1;

    public void addDefaultUsers(){
        users.put(1, new User("Masha123@gmail.com", "Masha123@gmail.com"));
        users.put(2, new User("Neshyna123@gmail.com", "Neshyna123@gmail.com"));
    }

    public User addUser(String email, String password){

        if (isMailExist(email)) {
            throw new IllegalArgumentException("Email already exists.");
        }
        User newUser = new User(email,password);
        users.put(userIdCounter++,newUser);
        return newUser;
    }

    public boolean isMailExist(String email) {
       if (users.values().stream()
                .map(User::getEmail)
                .allMatch(existEmail -> existEmail.equals(email))){
        return true;
       }
       return false;
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
                        user -> user.setRole(Role.ADMIN),
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
                .orElseThrow(() -> new IllegalArgumentException("User is not found."));
    }

    public Map<Integer, User> allUsers() {
        return users;
    }
}
