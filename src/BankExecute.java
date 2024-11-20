import repository.TransactionRepository;
import service.TransactionService;
import service.UserService;
import repository.UserRepository;

public class BankExecute {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        TransactionRepository transactionRepository = new TransactionRepository(userService);
        TransactionService transactionService = new TransactionService(transactionRepository, userService);

    }
}

