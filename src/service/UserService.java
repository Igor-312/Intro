package service;

import models.Account;
import models.Role;
import models.User;
import repository.UserRepository;
import utils.PersonValidate;
import utils.validatorExeptions.EmailValidateException;
import utils.validatorExeptions.PasswordValidatorException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    public User activeUser;
    private User user;
    private PersonValidate personValidator;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    @Override
    public Map<Integer, User> allUsers() {
        Map<Integer,User> allUsers = userRepository.allUsers();
        return allUsers;
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
        activeUser = user;
        System.out.println("User is successfully logged in.");
        return true;
    }

    @Override
    public Optional<User> registerUser(String email, String password) throws EmailValidateException, PasswordValidatorException {

        try {

        if (!personValidator.isEmailValid(email)) {
            System.out.println("Please check the email.");
            return Optional.empty();
        }
        if (!personValidator.isPasswordValid(password)) {
            System.out.println("Please check the password.");
            return Optional.empty();
        }
        if (!userRepository.isMailExist(email)){
            user = userRepository.addUser(email, password);
        }else {
            System.out.println("Email already exist");
        }
        }catch (EmailValidateException | PasswordValidatorException e) {
        System.out.println(e. getMessage());
        return Optional.empty();
    }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean isUserAdmin() {
        if (activeUser.getRole() != Role.ADMIN) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isUserBlocked() {
        if (activeUser == null) {
            // Если activeUser равен null, значит пользователь не авторизован.
            throw new IllegalStateException("User is not logged in.");
        }
        return activeUser.getRole() == Role.BLOCKED ;
    }

    @Override
    public void logout() {
        activeUser = null;
    }

    @Override
    public List<Account> getAccountsByUserId(int userId) {
        return userRepository.getAccountsByUserId(userId);
    }

    public User getActiveUser() {
        return activeUser;
    }

    // Метод doesAccountExist нужен для проверки существования счета
    // в системе через метод addTransaction в классе TransactionRepository
 //   public boolean doesAccountExist(int accountID){
    // Получаем все счета пользователя
//    List<Account> accounts = getAccountsByUserId(activeUser.getUserId()); // где userId — это ID текущего пользователя
//    // Проверяем, есть ли среди счетов счет с таким accountID
//    return accounts.stream()
//            .anyMatch(account -> account.getAccountId() == accountID); // Если хотя бы один счет с таким ID, возвращаем true
}


