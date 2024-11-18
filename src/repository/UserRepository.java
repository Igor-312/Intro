package repository;

import models.Account;
import models.Role;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository implements UserRepoInterface{
    private static Map<Integer, User> users = new HashMap<>();
    private static int userIdCounter = 1;
    private AccountRepository accountRepository;

    public UserRepository() {
        this.accountRepository = new AccountRepository();
    }

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

    @Override
    public List<Account> getAccountsByUserId(int userId) {
        List<Account> userAccounts = users.values().stream()
                .filter(user -> user.getUserId() == userId)
                .map(user -> user.getUserAccounts())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (userAccounts == null) {
            throw new IllegalStateException("The list of user accounts is null");
        }
        return userAccounts;
    }

    public Account createAccountForUser(int userId, String accountType, double initialBalance) {

        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        Account newAccount = accountRepository.createAccount(accountType,initialBalance);
        user.addUserAccount(newAccount);
        return newAccount;
    }
}
