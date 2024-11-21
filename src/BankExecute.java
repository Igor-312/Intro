import repository.AccountRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import service.AccountService;
import service.CurrencyService;
import service.TransactionService;
import service.UserService;
import utils.UserNotFoundException;
import utils.validatorExeptions.EmailValidateException;
import utils.validatorExeptions.PasswordValidatorException;
import view.ConsoleView;

public class BankExecute {
    public static void main(String[] args) throws EmailValidateException, PasswordValidatorException, UserNotFoundException {
        UserRepository userRepository = new UserRepository();
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository(userRepository);
        UserService userService = new UserService(userRepository);
        CurrencyService currencyService = new CurrencyService();
        AccountService accountService = new AccountService(accountRepository, transactionRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, userRepository);

        ConsoleView consoleView = new ConsoleView(accountService, transactionService, userService, currencyService);
        consoleView.run();
    }
}
