package models;

public class Account {
    private int accountId;
    private CurrencyCode currency;
    private double balance;
    private int userId;

    public Account(int accountId,CurrencyCode currency, double balance,int userId) {
        this.accountId = accountId;

        this.currency = currency;
        this.balance = balance;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format("Account ID: %d | Currency: %s | Balance: %.2f | User ID: %d",
                accountId, currency, balance, userId);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
