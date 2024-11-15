package view;

import models.Account;
import models.ExchangeRate;
import models.Transaction;
import models.User;
import service.CurrencyService;


import service.AccountService;
import service.TransactionService;
import service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleView(AccountService accountService, TransactionService transactionService, UserService userService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    private void waitRead() {
        System.out.println("\nPress enter to proceed.");
        scanner.nextLine();
    }

    public void run(){
        showLoginPage();
    }

    private void showLoginPage() {
        while (true) {

            System.out.println("Welcome!");

            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.println("\nSelect an option.");

            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) {
                System.out.println("Good bye!");
                System.exit(0);//end app work
            }
            handleLoginPageChoice(input);
        }
    }

    private void handleLoginPageChoice(int input) {
        switch (input) {
            case 1:
                //authorization

                System.out.println("User authorization");

                System.out.println("Type your email:");
                String email2 = scanner.nextLine();
                System.out.println("Type your password:");
                String password1 = scanner.nextLine();

                boolean user1 = userService.loginUser(email2, password1);

                if (user1 == true) {
                    // System.out.println("User successfully logged in");
                    showHomePage();
                }
                waitRead();
                break;
            case 2:
                //registration

                System.out.println("New user registration");

                System.out.println("Insert email:");
                String email = scanner.nextLine();
                System.out.println("Insert password:");
                String password = scanner.nextLine();

                User user = userService.registerUser(email, password);

                if (user != null) {
                    System.out.println("Registered successfully! Please Login!");
                    showLoginPage();
                } else {

                    System.out.println("Registration failed.");
                    showLoginPage();
                }

                waitRead();
                break;

            default:

                System.out.println("\nIncorrect input, please enter a number!");
        }
    }

    private void showHomePage() {
        while (true) {

            System.out.println("Menu:");

            System.out.println("1. User menu");
            System.out.println("2. Admin menu");
            System.out.println("0. Logout");

            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) {
                break;
            }
            handleShowHomePageChoice(input);
        }
    }

    private void handleShowHomePageChoice(int input) {
        switch (input) {
            case 1:
                showUserMenu();
                break;
            case 2:
                if (userService.isUserAdmin()){
                    showAdminMenu();
                }else{
                    System.out.println("Admin menu is available only for admin.");
                }
                break;
            default:
                System.out.println("Select an option.");
        }
    }

    private void showUserMenu() {
        while (true) {

            System.out.println("User menu:");

            System.out.println("1. Create account USD");
            System.out.println("2. Create account EUR");
            System.out.println("3. Create account BTC");//bitcoin
            System.out.println("4. Add money");
            System.out.println("5. Withdraw money");
            System.out.println("6. Exchange currency");
            System.out.println("7. Show balance");
            System.out.println("8. History");
            System.out.println("9. Show currency exchange rates");
            System.out.println("10. Delete account");
            System.out.println("11. My accounts");


            System.out.println("0. Back");

            System.out.println("\n Select an option.");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            handleUserMenuChoice(input);
        }
    }

    private void handleUserMenuChoice(int input) {
        switch (input) {
            case 1:

                System.out.println("Create account USD");
                AccountService.createAccountUSD();
                waitRead();
                break;

            case 2:

                System.out.println("Create account EUR");
                AccountService.createAccountEUR();
                waitRead();
                break;

            case 3:

                System.out.println("Create account BTC");
                AccountService.createAccountBTC();
                waitRead();
                break;

            case 4:

                System.out.println("Add money");

                System.out.println("Select account (id):");
                int accountID = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Amount of money to add: ");
                double amountOfMoney = scanner.nextDouble();
                scanner.nextLine();

                TransactionService.addMoney(accountID,amountOfMoney);

                waitRead();
                break;

            case 5:
                System.out.println("Withdraw money");

                System.out.println("Select account (id):");
                accountID = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Amount of money to withdraw: ");
                amountOfMoney = scanner.nextDouble();
                scanner.nextLine();

                TransactionService.withdrawMoney();

                waitRead();
                break;

            case 6:

                System.out.println("Exchange currency");

                System.out.println("Amount of money to exchange:");
                amountOfMoney = scanner.nextDouble();

                System.out.println("Currency (from): ");
                String currencyFrom = scanner.nextLine();

                System.out.println("Currency (to): ");
                String currencyTo = scanner.nextLine();

                TransactionService.exchangeMoney(amountOfMoney,currencyFrom,currencyTo);

                waitRead();
                break;

            case 7:

                System.out.println("Show balance");

                System.out.println("Select account (id):");
                accountID = scanner.nextInt();
                scanner.nextLine();

                Map<Integer,List<Account>> account = AccountService.showBalance(accountID);
                System.out.println(account);

                waitRead();
                break;

            case 8:

                System.out.println("History");
                Map<Integer, List<Transaction>> history = TransactionService.showHistory();
                System.out.println(history);

                waitRead();
                break;

            case 9:

                System.out.println("Show currency exchange rates");
                Map<String,String> exchangeRates = CurrencyService.showExchangeRates();
                System.out.println(exchangeRates);

                waitRead();
                break;

            case 10:

                System.out.println("Delete account");

                System.out.println("Select account (id):");
                accountID = scanner.nextInt();
                scanner.nextLine();

                AccountService.deleteAccount(accountID);

                waitRead();
                break;

            case 11:

                System.out.println("My accounts");
                Map<Integer, List<Account>> myAccounts = AccountService.myAccounts();
                System.out.println(myAccounts);

                waitRead();
                break;
        }
    }

    private void showAdminMenu () {
        while (true) {

            System.out.println("Admin menu:");

            System.out.println("1. Change currency exchange rates");
            System.out.println("2. Give admin permissions");
            System.out.println("3. Block user");//bitcoin
            System.out.println("4. Find user");
            System.out.println("5. Show user history");
            System.out.println("6. Show all users");

            System.out.println("0. Back");

            System.out.println("\n Select an option.");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            handleUserMenuChoice(input);
        }
    }
    private void handleAdminMenuChoice(int input) {
        switch (input) {
            case 1:

                System.out.println("Change currency exchange rates");

                System.out.println("Select currency: ");
                String currency = scanner.nextLine();

                System.out.println("Change rate: ");
                Double rate = scanner.nextDouble();
                scanner.nextLine();

                waitRead();
                break;

            case 2:

                System.out.println("Give admin permissions");

                System.out.println("Insert user id: ");
                int userId = scanner.nextInt();
                scanner.nextLine();

                UserService.giveAdminPermissions(userId);

                waitRead();
                break;

            case 3:

                System.out.println("Block user");

                System.out.println("Insert user id: ");
                userId = scanner.nextInt();
                scanner.nextLine();

                UserService.blockUser(userId);

                waitRead();
                break;

            case 4:

                System.out.println("Find user");

                System.out.println("Insert user id: ");
                userId = scanner.nextInt();
                scanner.nextLine();

                UserService.findUser(userId);

                waitRead();
                break;

            case 5:

                System.out.println("Show user history");

                System.out.println("Insert user id: ");
                userId = scanner.nextInt();
                scanner.nextLine();

                Map<Integer, List<Transaction>> history = TransactionService.showUserHistory(userId);
                System.out.println(history);

                waitRead();
                break;

            case 6:

                System.out.println("Show all users");

                Map<Integer, User> users = UserService.allUsers();
                System.out.println(users);

                waitRead();
                break;


        }
    }




}
