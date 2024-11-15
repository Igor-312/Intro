package models;

import java.time.LocalDateTime;

public class Transaction {
    private final int transactionId;
    private final int accountId;
    private Enum type;
    private final double amount;
    private final LocalDateTime date;
    private final Currency currency;


    public Transaction(int transactionId, int accountId, double amount, LocalDateTime date, Currency currency) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.currency = currency;
    }
}
