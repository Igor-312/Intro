package models;

public class Account {
    private int accountId;
    private CurrencyCode currency;
    private double balance;


    public Account(int accountId,CurrencyCode currency, double balance) {
        this.accountId = accountId++;
        this.currency = currency;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
