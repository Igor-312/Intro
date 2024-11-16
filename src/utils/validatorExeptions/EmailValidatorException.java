package utils.validatorExeptions;

public class EmailValidatorException extends Exception{
    public EmailValidatorException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Email validate exception | " + super.getMessage();

    }
}
