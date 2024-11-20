import repository.AccountRepository;
import repository.TransactionRepository;
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

        UserService userService = new UserService();
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository(userService );
        CurrencyService currencyService = new CurrencyService();

        //  создаем TransactionService, передавая в него все зависимости
        TransactionService transactionService = new TransactionService(
                null,  // Временно передаем null для accountService
                transactionRepository,
                accountRepository,
                currencyService
        );

        //  создаем AccountService, передавая ему уже инициализированный transactionService
        AccountService accountService = new AccountService(transactionService, accountRepository);


        ConsoleView consoleView = new ConsoleView(accountService,transactionService,userService,currencyService);

        consoleView.run();
    }
}
