package unittest;

import models.User;
import models.User.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testAddUser() {
        User user = userService.addUser("test@example.com", "password");
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testIsMailExist() {
        userService.addUser("test@example.com", "password");
        assertTrue(userService.isMailExist("test@example.com"));
    }

    @Test
    public void testGetUserEmail() {
        userService.addUser("test@example.com", "password");
        User user = userService.getUserEmail("test@example.com");
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testGiveAdminPermissions() {
        User user = userService.addUser("test@example.com", "password");
        userService.giveAdminPermissions(user.getUserId());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void testBlockUser() {
        User user = userService.addUser("test@example.com", "password");
        userService.blockUser(user.getUserId());
        assertEquals(Role.BLOCKED, user.getRole());
    }

    @Test
    public void testFindUser() {
        User user = userService.addUser("test@example.com", "password");
        User foundUser = userService.findUser(user.getUserId());
        assertNotNull(foundUser);
        assertEquals(user.getUserId(), foundUser.getUserId());
    }
}
