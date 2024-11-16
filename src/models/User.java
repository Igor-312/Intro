package models;

public class User {

    private static int userIdCounter = 0;
    private int userId;
    private String email;
    private String password;
    private Role role;
    private boolean isBlocked;

    public User( String email, String password) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.isBlocked = false;
        this.userId = userIdCounter++;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isBlocked=" + isBlocked +
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

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }


}
