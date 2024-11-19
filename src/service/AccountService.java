package service;

import models.Account;

import models.CurrencyCode;
import models.User;
import repository.AccountRepoInterface;
import repository.AccountRepository;
import utils.UserNotFoundException;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.CurrencyCode.*;
import static repository.UserRepository.users;


public class AccountService implements AccountServiceInterface {

    // Репозиторий для работы с аккаунтами
    private AccountRepoInterface accountRepo;
    private UserService userService;


    // Метод для генерации уникального ID аккаунта
    private static int accountIdCounter = 1; // Статический счетчик для уникальных ID

    // Конструктор, принимающий репозиторий
    public AccountService() {
        this.accountRepo = new AccountRepository();
        this.userService = new UserService();

    }

    // Получение аккаунта по ID
    @Override
    public Account getAccountById(int accountId) {
        Account account = accountRepo.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }
        return account;
    }

    // Пополнение баланса
    @Override
    public void deposit(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        Account account = getAccountById(accountId);
        accountRepo.updateAccountBalance(accountId, amount); // Пополнение счета
    }

    // Снятие средств
    @Override
    public void withdraw(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        Account account = getAccountById(accountId);
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        accountRepo.updateAccountBalance(accountId, -amount); // Снятие средств с баланса
    }

    // Удаление аккаунта
    @Override
    public void deleteAccount(int accountId) {
        Account account = getAccountById(accountId);
        if (account != null) {
            accountRepo.deleteAccount(accountId); // Удаление аккаунта
        } else {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }
    }

    // Создание аккаунта в USD
    @Override
    public void createAccountUSD() throws UserNotFoundException{

        User user = userService.getActiveUser();
        if (user == null) {
            throw new UserNotFoundException(" User not found ");
        }

        Account account = accountRepo.createAccount(user.getUserId(),USD, 0.0);
    }

    // Создание аккаунта в EUR
    @Override
    public void createAccountEUR()throws UserNotFoundException {

        User user = userService.getActiveUser();
        if (user == null) {
            throw new UserNotFoundException(" User not found ");
        }
        Account account = accountRepo.createAccount(user.getUserId(),EUR, 0.0); // Инициализация с балансом 0
    }

    // Создание аккаунта в BTC
    @Override
    public void createAccountBTC() throws UserNotFoundException {

        User user = userService.getActiveUser();
        if (user == null) {
            throw new UserNotFoundException(" User not found ");
        }

        accountRepo.createAccount(user.getUserId(),BTC,0.0);
    }

    // Показать баланс для аккаунта
    @Override
    public Map<Integer, List<Account>> showBalance(int accountId) {
        Map<Integer, List<Account>> result = new HashMap<>();
        Account account = getAccountById(accountId);
        if (account != null) {
            result.put(accountId, List.of(account)); // Возвращаем только один аккаунт
        }
        return result;
    }

    // Показать все аккаунты
    @Override
    public Map<Integer, List<Account>> myAccounts() {
        Map<Integer, List<Account>> allAccounts = new HashMap<>();
        List<Account> accounts = accountRepo.getAllAccount();
        allAccounts.put(0, accounts);
        return allAccounts;
    }
}
