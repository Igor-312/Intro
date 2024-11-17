package unittest;

import models.Role;
import models.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.UserRepository;
import service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private Map<Integer,User> users;
    private User activeUser;

    @BeforeEach
    void setUp() {
        users = new HashMap<>();
        users.put(1, new User( "Masha123@gmail.com", "Masha123@gmail.com"));
        users.put(2, new User( "Neshyna123@gmail.com", "Neshyna123@gmail.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Masha123@gmail.com"})
    public void testEmailExists(String email) {
        assertTrue(userRepository.isMailExist(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"nonexistentemail@gmail.com"})
    public void testEmailDoesNotExist(String email) {
        assertFalse(userRepository.isMailExist(email));
    }

    @Test
    public void testLoginUser_SuccessfulLogin() {
        boolean result = userService.loginUser("Neshyna123@gmail.com", "Neshyna123@gmail.com");
        assertTrue(result);
    }

    @Test
    public void testLoginUser_InvalidEmail() {
        boolean result = userService.loginUser("nonexistent@example.com", "anyPassword");
        assertFalse(result);
    }

    @Test
    public void testLoginUser_IncorrectPassword() {
        boolean result = userService.loginUser("Neshyna123@gmail.com", "wrongPassword");
        assertFalse(result);
    }

    @Test
    public void testLoginUser_EmptyPassword() {
        boolean result = userService.loginUser("Neshyna123@gmail.com", "");
        assertFalse(result);
    }

    @Test
    public void testLoginUser_UserNotFound() {
        boolean result = userService.loginUser("notfound@example.com", "anyPassword");
        assertFalse(result);
    }

    @Test
    public void testAllUsers_WithUsers() {
        Map<Integer, User> result = userService.allUsers();
        assertNotNull(result);
    }

    @Test
    public void testGiveAdminPermissions(){
        User user1 = new User("User1@example.com", "User1@example.com");
        userService.giveAdminPermissions(3);//userId 3 as well as we already have users with id 1 and 2
        assertEquals(Role.ADMIN, user1.getRole());
    }

    @Test
    public void testBlockUser(){
        User user = new User("User2@example.com", "User2@example.com");
        userService.blockUser(3);//userId 3 as well as we already have users with id 1 and 2
        assertEquals(Role.BLOCKED, user.getRole());
    }

    @Test
    public void testFindUser(){
        User user = new User("Masha123@gmail.com", "Masha123@gmail.com");
        userService.findUser(1);
        assertNotNull(userService.findUser(1));
        assertEquals("Masha123@gmail.com",user.getEmail());
        assertEquals("Masha123@gmail.com",user.getPassword());
    }

    @Test
    public void testFindNotExistingUser(){
        //user with id 123 doesn't exist
        assertNull(userService.findUser(123));
    }

    @Test
    public void testIsUserAdmin_WhenAdmin(){
        User user = new User("Neshyna123@gmail.com", "Neshyna123@gmail.com");
        user = activeUser;
        assertTrue(userService.isUserAdmin());
    }

    @Test
    public void testIsUserAdmin_WhenNotAdmin(){
        User user = new User("Masha123@gmail.com", "Masha123@gmail.com");
        user = activeUser;
        assertFalse(userService.isUserAdmin());
    }








}
