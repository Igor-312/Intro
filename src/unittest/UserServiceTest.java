package unittest;

import models.Role;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.UserRepository;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private Map<Integer, User> users;
    private User activeUser;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        users = new HashMap<>();
        users.put(1, new User("Masha123@gmail.com", "password123"));
        users.put(2, new User("Neshyna123@gmail.com", "password123"));

        // Настройка моков для репозитория
        when(userRepository.getUserEmail("Masha123@gmail.com")).thenReturn(users.get(1));
        when(userRepository.getUserEmail("Neshyna123@gmail.com")).thenReturn(users.get(2));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Masha123@gmail.com"})
    public void testEmailExists(String email) {
        when(userRepository.isMailExist(email)).thenReturn(true);
        assertTrue(userRepository.isMailExist(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"nonexistentemail@gmail.com"})
    public void testEmailDoesNotExist(String email) {
        when(userRepository.isMailExist(email)).thenReturn(false);
        assertFalse(userRepository.isMailExist(email));
    }

    @Test
    public void testLoginUser_SuccessfulLogin() {
        User user = userService.loginUser("Neshyna123@gmail.com", "password123");
        assertNotNull(user);
        assertEquals("Neshyna123@gmail.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testLoginUser_InvalidEmail() {
        when(userRepository.getUserEmail("nonexistent@example.com")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.loginUser("nonexistent@example.com", "anyPassword");
        });
        assertEquals("Invalid email or password.", exception.getMessage());
    }

    @Test
    public void testLoginUser_IncorrectPassword() {
        User mockUser = new User("Neshyna123@gmail.com", "password123");
        when(userRepository.getUserEmail("Neshyna123@gmail.com")).thenReturn(mockUser);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.loginUser("Neshyna123@gmail.com", "wrongPassword");
        });
        assertEquals("Invalid email or password.", exception.getMessage());
    }

    @Test
    public void testLoginUser_EmptyPassword() {
        User mockUser = new User("Neshyna123@gmail.com", "password123");
        when(userRepository.getUserEmail("Neshyna123@gmail.com")).thenReturn(mockUser);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.loginUser("Neshyna123@gmail.com", "");
        });
        assertEquals("Invalid email or password.", exception.getMessage());
    }

    @Test
    public void testLoginUser_UserNotFound() {
        when(userRepository.getUserEmail("notfound@example.com")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.loginUser("notfound@example.com", "anyPassword");
        });
        assertEquals("Invalid email or password.", exception.getMessage());
    }

    @Test
    public void testAllUsers_WithUsers() {
        when(userRepository.allUsers()).thenReturn(users);
        Map<Integer, User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGiveAdminPermissions() {
        User user1 = new User("User1@example.com", "password123");
        when(userRepository.findUser(3)).thenReturn(user1);
        userService.giveAdminPermissions(3); // userId 3 as we already have users with id 1 and 2
        assertEquals(Role.ADMIN, user1.getRole());
    }

    @Test
    public void testBlockUser() {
        User user = new User("User2@example.com", "password123");
        when(userRepository.findUser(3)).thenReturn(user);
        userService.blockUser(3); // userId 3 as we already have users with id 1 and 2
        assertEquals(Role.BLOCKED, user.getRole());
    }

    @Test
    public void testFindUser() {
        User user = new User("Masha123@gmail.com", "password123");
        when(userRepository.findUser(1)).thenReturn(user);

        User foundUser = userService.findUser(1);
        assertNotNull(foundUser);
        assertEquals("Masha123@gmail.com", foundUser.getEmail());
        assertEquals("password123", foundUser.getPassword());
    }

    @Test
    public void testFindNotExistingUser() {
        when(userRepository.findUser(123)).thenReturn(null);

        User user = userService.findUser(123);
        assertNull(user);
    }

    @Test
    public void testIsUserAdmin_WhenAdmin() {
        User user = new User("Neshyna123@gmail.com", "password123");
        user.setRole(Role.ADMIN);
        when(userRepository.getUserEmail("Neshyna123@gmail.com")).thenReturn(user);
        userService.loginUser("Neshyna123@gmail.com", "password123");
        activeUser = user;

        assertTrue(userService.isUserAdmin());
    }

    @Test
    public void testIsUserAdmin_WhenNotAdmin() {
        User user = new User("Masha123@gmail.com", "password123");
        user.setRole(Role.USER);
        when(userRepository.getUserEmail("Masha123@gmail.com")).thenReturn(user);
        userService.loginUser("Masha123@gmail.com", "password123");
        activeUser = user;

        assertFalse(userService.isUserAdmin());
    }
}
