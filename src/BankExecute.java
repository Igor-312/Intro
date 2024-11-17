import service.AccountService;
import service.CurrencyService;
import service.TransactionService;
import service.UserService;
import view.ConsoleView;

public class BankExecute {
    public static void main(String[] args) {
        UserService userService = new UserService();
        AccountService accountService = new AccountService();
        CurrencyService currencyService = new CurrencyService();
        TransactionService transactionService = new TransactionService();

        ConsoleView consoleView = new ConsoleView(accountService,transactionService,userService,currencyService);

        consoleView.run();
    }
}
