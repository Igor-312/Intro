package repository;

import models.User;
import models.Account;
import models.Currency;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> users = new ArrayList<>();
    private AccountRepoInterface accountRepo;

    public UserRepository() {
        this.accountRepo = new AccountRepository();
    }

    // Метод для получения всех пользователей
    public List<User> allUsers() {
        return new ArrayList<>(users);
    }

    // Метод для добавления пользователя
    public void addUser(User user) {
        users.add(user);
    }

    // Метод для создания аккаунта
    public void createAccount(String currencyCode, double initialBalance) {
        Currency currency = new Currency(currencyCode, "Currency Name");
        Account account = new Account(generateAccountId(), currency, initialBalance, new User("default@example.com", "defaultPassword"));
        accountRepo.createAccount(account);
    }

    // Метод для генерации уникального ID
    private int generateAccountId() {
        // Имплементация метода генерации уникальных ID (пример)
        return (int) (Math.random() * 1000); // Пример возвращения случайного ID
    }
}
