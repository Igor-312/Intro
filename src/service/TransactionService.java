package service;
import models.Account;
import models.Currency;
import models.CurrencyCode;
import models.Transaction;
import repository.AccountRepository;
import repository.TransactionRepository;


import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.stream.Collectors;

import java.util.List;
import java.util.Map;

import static models.CurrencyCode.USD;

public class TransactionService implements TransactionServiceInterface {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final CurrencyService currencyService;

   public TransactionService(AccountService accountService,
                       TransactionRepository transactionRepository,
                       AccountRepository accountRepository,
                       CurrencyService currencyService) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.currencyService = currencyService;
    }

    @Override
    public void addMoney(int accountID, double amountOfMoney) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }

        // Получение аккаунта через AccountService
        Account account = accountService.getAccountById(accountID);
        if (account == null) {
            throw new IllegalArgumentException("Account not found with ID: " + accountID);
        }

        // Обновление баланса аккаунта
        accountService.deposit(accountID, amountOfMoney);

        // Создание новой транзакции
        Transaction newTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                amountOfMoney,
                LocalDateTime.now(),
                new Currency(account.getCurrency(), account.getCurrency().toString()) // Генерация объекта Currency
        );

        // Добавление транзакции в репозиторий
        transactionRepository.addTransaction(accountID, newTransaction);

        System.out.println("Successfully added " + amountOfMoney + " on account ID: " + accountID);
    }

    @Override
    public void withdrawMoney(int accountID, double amountOfMoney) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }
        // Получаем текущий баланс счета
        double currentBalance = accountRepository.getAccountBalance(accountID);
        if (currentBalance < amountOfMoney) {
            throw new IllegalArgumentException("Not enough funds on the account.");
        }

        // Обновление баланса после транзакции (если требуется)
        accountRepository.updateAccountBalance(accountID, -amountOfMoney);

        // Создание транзакции на снятие средств
        Transaction withdrawalTransaction = new Transaction(
                generateTransactionId(),
                accountID,
                -amountOfMoney, // Отрицательное значение для снятия
                LocalDateTime.now(),
                new Currency(USD, "Доллар США")
        );

        // Добавление транзакции в репозиторий
        transactionRepository.addTransaction(accountID, withdrawalTransaction);
        System.out.println("Successfully withdrew " + amountOfMoney + " from the account ID: " + accountID);
    }

    // Метод для получения курса обмена
    private double getExchangeRate(CurrencyCode fromCurrency, CurrencyCode toCurrency) {
        double fromRate = currencyService.getExchangeRate(fromCurrency.name());
        double toRate = currencyService.getExchangeRate(toCurrency.name());

        if (fromRate <= 0 || toRate <= 0) {
            throw new IllegalArgumentException("Invalid or unsupported currency rates for: " + fromCurrency + " to " + toCurrency);
        }

        return toRate / fromRate; // Курс обмена между валютами
    }

    @Override
    public void exchangeMoney(double amountOfMoney, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        if (amountOfMoney <= 0) {
            throw new IllegalArgumentException("The amount of money must be greater than zero.");
        }
        // Получение аккаунтов по валютам
        Account fromAccount = accountRepository.getAccountByCurrency(currencyFrom);
        Account toAccount = accountRepository.getAccountByCurrency(currencyTo);

        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Account for the specified currency not found.");
        }

        // Проверка, достаточно ли средств на исходном аккаунте
        if (fromAccount.getBalance() < amountOfMoney) {
            throw new IllegalArgumentException("Insufficient funds in " + currencyFrom + " account.");
        }


        // Получение курса обмена
        double exchangeRate = getExchangeRate(currencyFrom, currencyTo);

        // Вычисление конвертированной суммы
        double convertedAmount = amountOfMoney * exchangeRate;

        // Создание объекта Currency для результата
        Currency resultingCurrency = new Currency(currencyTo, currencyTo.name());

        // Создание транзакции
        Transaction exchangeTransaction = new Transaction(
                generateTransactionId(),
                0, // Специальный ID для транзакций обмена валют
                convertedAmount,
                LocalDateTime.now(),
                new Currency(currencyTo, currencyTo.name())
        );

        // Обновление баланса на исходном счете (вычитание суммы)
        fromAccount.setBalance(fromAccount.getBalance() - amountOfMoney);

        // Обновление баланса на целевом счете (прибавление конвертированной суммы)
        toAccount.setBalance(toAccount.getBalance() + convertedAmount);

        // Сохранение транзакции и обновленных аккаунтов
        transactionRepository.save(exchangeTransaction); // сохраняем транзакцию
        accountRepository.save(fromAccount); // сохраняем обновленный аккаунт
        accountRepository.save(toAccount); // сохраняем обновленный аккаунт

        System.out.println("Exchanged " + amountOfMoney + " " + currencyFrom + " to " + currencyTo + " at the rate " + exchangeRate);
        System.out.println("Converted amount: " + convertedAmount + " " + currencyTo);
        System.out.println("Money has been exchanged");
    }

    @Override
    public Map<Integer, List<Transaction>> showHistory() {
        // Извлекаем все транзакции
        List<Transaction> transactions = transactionRepository.getAllTransactions();

        // Вывод всех транзакций
        System.out.println("Все транзакции:");
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("Нет доступных транзакций.");
        } else {
            // Печать всех транзакций (если они есть)
            transactions.forEach(transaction -> System.out.println(transaction));
        }

        // Группируем транзакции по accountId
        Map<Integer, List<Transaction>> history = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId));

        // Если нет сгруппированных данных
        if (history.isEmpty()) {
            System.out.println("Нет доступных транзакций.");
        } else {

            // Форматированный вывод для сгруппированных транзакций
            history.forEach((accountId, transactionList) -> {
                System.out.println("\nAccount ID: " + accountId);  // Выводим ID аккаунта
                transactionList.forEach(transaction -> {
                    System.out.println(String.format("  %s", transaction));  // Выводим транзакцию с отступом
                });
            });
        }

        return history;
    }

    @Override
    public Map<Integer, List<Transaction>> showUserHistory(int userId) {
        return transactionRepository.getAllTransactions()
                .stream()
                .filter(transaction -> transaction.getUserId() == userId)
                .collect(Collectors.groupingBy(Transaction::getAccountId));
    }


    // AtomicInteger гарантирует, что счетчик будет работать корректно в многозадачных приложениях.
    private static final AtomicInteger transactionIdCounter = new AtomicInteger(0);

    private int generateTransactionId() {
        return transactionIdCounter.incrementAndGet();
    }
}

