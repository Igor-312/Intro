package models;

import java.time.LocalDateTime;

public class Transaction {
    private final int transactionId;
    private final int accountId;
    private TypeTransaction type; // Изменяем тип на TypeTransaction
    private final double amount;
    private final LocalDateTime date;
    private final Currency currency;
    private int userId;

    // Конструктор без указания типа транзакции
    public Transaction(int transactionId, int accountId, double amount, LocalDateTime date, Currency currency) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.currency = currency;
    }

    // Конструктор с указанием типа транзакции
    public Transaction(int transactionId, int accountId, double amount, LocalDateTime date, Currency currency, TypeTransaction type) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.currency = currency;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                ", currency=" + currency +
                ", userId=" + userId +
                '}';
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public TypeTransaction getType() {
        return type;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
