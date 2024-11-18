import service.AccountService;
import service.CurrencyService;
import service.TransactionService;
import service.UserService;
import utils.validatorExeptions.EmailValidateException;
import utils.validatorExeptions.PasswordValidatorException;
import view.ConsoleView;
import repository.TransactionRepository;
import repository.UserRepository;

public class BankExecute {
    public static void main(String[] args) throws EmailValidateException, PasswordValidatorException {
        UserRepository userRepository = new UserRepository(); // Создаем экземпляр UserRepository
        UserService userService = new UserService(userRepository); // Передаем его в конструктор UserService
        AccountService accountService = new AccountService();
        CurrencyService currencyService = new CurrencyService();
        TransactionRepository transactionRepository = new TransactionRepository();
        TransactionService transactionService = new TransactionService(transactionRepository);

        ConsoleView consoleView = new ConsoleView(accountService, transactionService, userService, currencyService);

        consoleView.run();
    }
}
