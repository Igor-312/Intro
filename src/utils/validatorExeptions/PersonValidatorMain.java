package utils.validatorExeptions;

import utils.PersonValidator;

public class PersonValidatorMain {
    public static void main(String[] args) {

        //receive email & password ?
        String email = "";//delete later
        String password = "";//delete later

        try {
            PersonValidator.isEmailValid(email);
            System.out.println("Email is valid");

            PersonValidator.isPasswordValid(password);
            System.out.println("Password is valid");

        } catch (EmailValidatorException exception) {

            System.out.println("Email is not valid");
            System.out.println(exception.getMessage());
            System.out.println("Insert email");

        } catch (PasswordValidatorException ex) {

            System.out.println("Password is not valid");
            System.out.println(ex.getMessage());
            System.out.println("Insert password");

        }
    }
}
