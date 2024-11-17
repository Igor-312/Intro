package repository;

import models.Account;
import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepository implements AccountRepoInterface {
    private Map<Integer, List<Account>> accounts = new HashMap<>();
    //integer = userID



}
