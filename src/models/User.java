package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String email;
    private String password;
    private Role role;
    private boolean isBlocked;
    private final List<User> users = new ArrayList<>();

    public User(int id, String email, String password, Role role, boolean isBlocked) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isBlocked = isBlocked;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isBlocked=" + isBlocked +
                ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && isBlocked == user.isBlocked && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(users, user.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role, isBlocked, users);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public List<User> getUsers() {
        return users;
    }
}
