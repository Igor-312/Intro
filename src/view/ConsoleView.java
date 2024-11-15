package view;

import models.Account;
import models.Transaction;
import models.User;
import utils.PersonValidate;


import service.AccountService;
import service.TransactionService;
import service.UserService;

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
            System.out.println("7. History");
            System.out.println("8. Delete account");

            System.out.println("0. Back");

            System.out.println("\n Select an option.");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            handleUserMenuChoice(input);
        }
    }

    private void handleUserMenuChoice(int input) {
    }

    private void showAdminMenu () {}
    private void handleAdminMenuChoice(int input) {}



}
