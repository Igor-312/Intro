package unittest;

import models.User;
import service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private UserService userService;
    private User adminUser;
    private User regularUser;

    String startEmail = "Neshyna123@gmail.com";
    String startPassword = "Neshyna123@gmail.com";

    @BeforeEach
    void setUp() {

    }



}
