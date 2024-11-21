package utils;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "User not found" + super.getMessage();
    }
}
