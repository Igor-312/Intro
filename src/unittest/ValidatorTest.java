package unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.PersonValidate;
import utils.validatorExeptions.EmailValidateException;
import utils.validatorExeptions.PasswordValidatorException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTest {
    PersonValidate testUser;
    String startEmail = "Neshyna@test.com";
    String startPassword = "Neshyna100%";  // Пароль с включенным специальным символом

    @BeforeEach
    void setUp() throws EmailValidateException, PasswordValidatorException {
        testUser = new PersonValidate(startEmail, startPassword);
    }

    @Test
    void testValidEmailSet() throws EmailValidateException {
        String validEmail = "Valid123@test.com";
        testUser.setEmail(validEmail);
        assertEquals(validEmail, testUser.getEmail());
    }

    @ParameterizedTest
    @MethodSource("invalidEmailData")
    void testInvalidEmailSet(String invalidEmail) {
        try {
            testUser.setEmail(invalidEmail);
        } catch (EmailValidateException e) {
            // Ожидаемое исключение
        }
        assertNotEquals(invalidEmail, testUser.getEmail());
        assertEquals(startEmail, testUser.getEmail());
    }

    static Stream<String> invalidEmailData() {
        return Stream.of(
                "testmail.net",
                "test@@mail.net",
                "test@mai@l.net",
                "test@mailnet",
                "test@mail.ne.t",
                "test@mail.net.",
                "test@ mail.net",
                "test@ma!il.net"
        );
    }

    @Test
    void testValidPasswordSet() throws PasswordValidatorException {
        String validPassword = "Test_123!";  // Пароль с включенным специальным символом
        testUser.setPassword(validPassword);
        assertEquals(validPassword, testUser.getPassword());
    }

    @ParameterizedTest
    @MethodSource("invalidPassword")
    void testInvalidPassword(String invalidPassword) {
        try {
            testUser.setPassword(invalidPassword);
        } catch (PasswordValidatorException e) {
            // Ожидаемое исключение
        }
        assertEquals(startPassword, testUser.getPassword());
        assertNotEquals(invalidPassword, testUser.getPassword());
    }

    static Stream<String> invalidPassword() {
        return Stream.of(
                "Test_1",
                "Test_test",
                "TEST_123",
                "test_123",
                "Test123456"
        );
    }

    @Test
    void testInvalidEmailException() {
        String invalidEmail = "invalid-email";
        EmailValidateException thrown = assertThrows(
                EmailValidateException.class,
                () -> testUser.setEmail(invalidEmail),
                "Expected setEmail() to throw, but it didn't"
        );

        assertEquals("Email validate exception @ error", thrown.getMessage());
    }

    @Test
    void testInvalidPasswordException() {
        String invalidPassword = "short";
        PasswordValidatorException thrown = assertThrows(
                PasswordValidatorException.class,
                () -> testUser.setPassword(invalidPassword),
                "Expected setPassword() to throw, but it didn't"
        );

        assertEquals("Password validate exception: length error", thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidPasswordSpecialSymbols")
    void testInvalidPasswordSpecialSymbols(String invalidPassword) {
        PasswordValidatorException thrown = assertThrows(
                PasswordValidatorException.class,
                () -> testUser.setPassword(invalidPassword),
                "Expected setPassword() to throw, but it didn't"
        );

        assertEquals("Password validate exception: special symbol error", thrown.getMessage());
    }

    static Stream<String> invalidPasswordSpecialSymbols() {
        return Stream.of(
                "Test1234",  // Пароль с минимальной длиной, но без специального символа
                "Password1",
                "Nopunctu8"
        );
    }
}
