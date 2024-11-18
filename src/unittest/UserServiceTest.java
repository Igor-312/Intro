package unittest;

import models.Role;
import models.User;
import repository.UserRepository;
import service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);

        when(userRepository.allUsers()).thenReturn(new HashMap<>());
        when(userRepository.isMailExist("Masha123@gmail.com")).thenReturn(true);
        when(userRepository.isMailExist("Neshyna123@gmail.com")).thenReturn(true);
        when(userRepository.isMailExist("nonexistentemail@gmail.com")).thenReturn(false);
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
        when(userRepository.getUserEmail("Neshyna123@gmail.com")).thenReturn(new User("Neshyna123@gmail.com", "Neshyna123@gmail.com"));
        boolean result = userService.loginUser("Neshyna123@gmail.com", "Neshyna123@gmail.com");
        assertTrue(result);
    }

    @Test
    public void testLoginUser_InvalidEmail() {
        when(userRepository.getUserEmail("nonexistent@example.com")).thenThrow(new IllegalArgumentException("Email not found."));
        boolean result = userService.loginUser("nonexistent@example.com", "anyPassword");
        assertFalse(result);
    }

    @Test
    public void testLoginUser_IncorrectPassword() {
        when(userRepository.getUserEmail("Neshyna123@gmail.com")).thenReturn(new User("Neshyna123@gmail.com", "Neshyna123@gmail.com"));
        boolean result = userService.loginUser("Neshyna123@gmail.com", "wrongPassword");
        assertFalse(result);
    }

    @Test
    public void testLoginUser_EmptyPassword() {
        when(userRepository.getUserEmail("Neshyna123@gmail.com")).thenReturn(new User("Neshyna123@gmail.com", "Neshyna123@gmail.com"));
        boolean result = userService.loginUser("Neshyna123@gmail.com", "");
        assertFalse(result);
    }

    @Test
    public void testLoginUser_UserNotFound() {
        when(userRepository.getUserEmail("notfound@example.com")).thenThrow(new IllegalArgumentException("Email not found."));
        boolean result = userService.loginUser("notfound@example.com", "anyPassword");
        assertFalse(result);
    }

    @Test
    public void testAllUsers_WithUsers() {
        Map<Integer, User> mockUsers = new HashMap<>();
        mockUsers.put(1, new User("Masha123@gmail.com", "Masha123@gmail.com"));
        mockUsers.put(2, new User("Neshyna123@gmail.com", "Neshyna123@gmail.com"));
        when(userRepository.allUsers()).thenReturn(mockUsers);

        Map<Integer, User> result = userService.allUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGiveAdminPermissions() {
        doNothing().when(userRepository).giveAdminPermissions(3);
        userService.giveAdminPermissions(3);
        verify(userRepository, times(1)).giveAdminPermissions(3);
    }

    @Test
    public void testBlockUser() {
        doNothing().when(userRepository).blockUser(3);
        userService.blockUser(3);
        verify(userRepository, times(1)).blockUser(3);
    }

    @Test
    public void testFindUser() {
        User user = new User("Masha123@gmail.com", "Masha123@gmail.com");
        when(userRepository.findUser(1)).thenReturn(user);
        User result = userService.findUser(1);
        assertNotNull(result);
        assertEquals("Masha123@gmail.com", result.getEmail());
        assertEquals("Masha123@gmail.com", result.getPassword());
    }

    @Test
    public void testFindNotExistingUser() {
        when(userRepository.findUser(123)).thenThrow(new IllegalArgumentException("User not found."));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findUser(123);
        });
        assertEquals("User not found.", exception.getMessage());
    }

    @Test
    public void testIsUserAdmin_WhenAdmin() {
        User user = new User("Neshyna123@gmail.com", "Neshyna123@gmail.com");
        user.setRole(Role.ADMIN);
        userService.setActiveUser(user);
        assertTrue(userService.isUserAdmin());
    }

    @Test
    public void testIsUserAdmin_WhenNotAdmin() {
        User user = new User("Masha123@gmail.com", "Masha123@gmail.com");
        userService.setActiveUser(user);
        assertFalse(userService.isUserAdmin());
    }
}
