package models;

import java.util.List;
import java.util.Objects;

public class User {

    private int userId;
    private String email;
    private String password;
    private Role role;

    private List<Account> userAccounts;

    public User(String email, String password, int userId) {
        this.email = email;
        this.password = password;
        this.userAccounts = userAccounts;
        this.role = role;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", userAccounts=" + userAccounts +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Account> getUserAccounts() {
        return userAccounts;
    }

    public void addUserAccount(Account accounts) {
        this.userAccounts.add(accounts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(userAccounts, user.userAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password, role, userAccounts);
    }
}
