package unittest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.PersonValidator;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ValidatorTest {
    PersonValidator testUser;
    String startEmail = "Neshyna@test.com";
    String startPassword = "Neshyna100%";

    @BeforeEach
    void setUp() {
        testUser = new PersonValidator(startEmail,startPassword);
    }

    @Test
    void testValidEmailSet() {
        String validEmail = "Valid123@test.com";
        testUser.setEmail(validEmail);
        assertEquals(validEmail, testUser.getEmail());
    }

    @ParameterizedTest
    @MethodSource("invalidEmailData")
    void testInvalidEmailSet(String invalidEmail) {
        testUser.setEmail(invalidEmail);
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
    void testValidPasswordSet() {
        String validPassword = "Test_123";
        testUser.setPassword(validPassword);
        assertEquals(validPassword, testUser.getPassword());
    }

    @ParameterizedTest
    @MethodSource("invalidPassword")
    void testInvalidPassword(String invalidPassword) {
        testUser.setPassword(invalidPassword);
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
}
