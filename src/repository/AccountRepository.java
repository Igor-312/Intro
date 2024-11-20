package repository;

import models.Account;
import models.Currency;
import models.CurrencyCode;
import models.User;
import service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static repository.UserRepository.users;

public class AccountRepository implements AccountRepoInterface {

    // Хранилище аккаунтов (Map или List)
    private Map<Integer, Account> accounts = new HashMap<>();
    private Currency currency;
    private final UserService userService;
    private static int incrementAccountId = 1;

    public AccountRepository() {
        this.userService = new UserService();
    }

    // Возвращает баланс по ID аккаунта
    public double getAccountBalance(int accountId) { // Нужен в TransactionService для метода withdrawMoney чтоб получить тек.баланс счета
        Account account = accounts.get(accountId);
        if (account != null) {
            return account.getBalance();  // Получение баланса у аккаунта
        }
        throw new IllegalArgumentException("Account not found.");
    }

    // Создать аккаунт
    @Override
    public Account createAccount(CurrencyCode currency, double initialBalance) {

        int accountId = incrementAccountId++;
        Account account = new Account(currency, initialBalance);

        // Проверка на существование аккаунта с таким ID
        if (account != null && !accounts.containsKey(accountId)) {
            accounts.put(account.getAccountId(), account); // Добавление аккаунта в Map
            //createAccountForUser(currency,initialBalance);

        } else {
            // Логирование или выбрасывание исключения, если аккаунт с таким ID уже существует
            throw new IllegalArgumentException("Account with this ID already exists or account is null.");
        }
        return account;
    }

    public Account createAccountForUser(CurrencyCode currency, double initialBalance) {

        User user = users.get(userService.getActiveUser.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        Account newAccount = createAccount(currency,initialBalance);
        user.addUserAccount(newAccount);
        return newAccount;
    }

    // Получить аккаунт по ID
    @Override
    public Account getAccountById(int accountId) {
        return accounts.get(accountId); // Доступ к Map по ключу
    }

    // Получить все аккаунты
    @Override
    public List<Account> getAllAccount() {
        return new ArrayList<>(accounts.values()); // Конвертация Map в List
    }

    // Пополнение баланса
    @Override
    public boolean updateAccountBalance(int accountId, double amount) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            return true;
        }
        return false;
    }

    // Удалить аккаунт
    @Override
    public void deleteAccount(int accountId) {
        accounts.remove(accountId); // Удаление из Map
    }

}
