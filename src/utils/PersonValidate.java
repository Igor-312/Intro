package utils;

import utils.validatorExeptions.EmailValidateException;
import utils.validatorExeptions.PasswordValidatorException;

public class PersonValidate {

    private String email;
    private String password;

    public PersonValidate(String email, String password) throws EmailValidateException, PasswordValidatorException {
        setEmail(email);
        setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmailValidateException {

        if (isEmailValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws PasswordValidatorException {
        if (isPasswordValid(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Invalid password format");
        }
    }

    public static boolean isEmailValid(String email) throws EmailValidateException {
        // 1. Должна присутствовать @
        int indexAt = email.indexOf('@');
        // int lastAt = email.lastIndexOf('@');
        if (indexAt == -1 || indexAt != email.lastIndexOf('@')) throw new EmailValidateException("@ error");

        // 2. Точка после собаки
        int dotIndexAfterAt = email.indexOf('.', indexAt + 1);
        if (dotIndexAfterAt == -1) throw new EmailValidateException(". after @ error");

        // 3. После последней точки есть 2 или более символов
        // test@fazx.com.ne.t
        int lastDotIndex = email.lastIndexOf('.');
        if (lastDotIndex + 2 >= email.length()) throw new EmailValidateException("last . error");
        // 4.  Алфавит, цифры, '-', '_', '.', '@'
        /*
        Я беру каждый символ. Проверяю, что он не является "запрещенным"
        И если нахожу не подходящий символ - возвращаю false
         */
        for (int i = 0; i < email.length(); i++) {
            char ch = email.charAt(i);

            // Если символ удовлетворяет одному из условий на "правильность"
            boolean isPass = (Character.isAlphabetic(ch) ||
                    Character.isDigit(ch) ||
                    ch == '-' ||
                    ch == '_' ||
                    ch == '.' ||
                    ch == '@');

            // Если любой символ НЕ подходящий, сразу возвращаем false
            if (!isPass) throw new EmailValidateException("illegal symbol");
        }

        // 5. До собаки должен быть хотя бы 1 символ == собака не первая в строке. Т.е. ее индекс не равен 0
        if (indexAt == 0) throw new EmailValidateException("@ should not first");
        // 6. Первый символ - должна быть буква
        // Если 0-й символ НЕ является буквой, то email не подходит = return false;
        char firstChar = email.charAt(0);
        if (!Character.isAlphabetic(firstChar)) throw new EmailValidateException("first symbol should be alphabetic");
        return true;
    }
  
    public static boolean isPasswordValid(String password) throws PasswordValidatorException {
        if (password == null || password.length() < 8) {
            System.out.println("Password should be at least 8 characters");
            throw new PasswordValidatorException("length error");

        }

        boolean isDigit = false;
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        boolean isSpecialSymbol = false;

        boolean[] result = new boolean[4]; // false, false, false, false

        String symbols = "!%$@&*()[].,-";

        //throw exception can't be declared at for loop, better after check conditions are done
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isDigit(ch)) isDigit = true;
            if (Character.isUpperCase(ch)) isUpperCase = true;
            if (Character.isLowerCase(ch)) isLowerCase = true;
            if (symbols.indexOf(ch) >= 0) isSpecialSymbol = true;
        }

        System.out.printf("%s | %s | %s | %s\n", isDigit, isUpperCase, isLowerCase, isSpecialSymbol);

        if (!isDigit) throw new PasswordValidatorException("digit error");
        if (!isUpperCase) throw new PasswordValidatorException("uppercase error");
        if (!isLowerCase) throw new PasswordValidatorException("lowercase error");
        if (!isSpecialSymbol) throw new PasswordValidatorException("special symbol error");

        return isDigit && isUpperCase && isLowerCase && isSpecialSymbol;
    }

}
