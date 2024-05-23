package systemTest;

import com.virtualbank.service.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserLoginRegisterTest {

    // create a new UserService object for testing
    UserService userService = new UserService();

    // test the login function
    @Test
    public void testLogin() {
        // prepare test data
        String username = "testUser";
        String password = "password123";

        // execute the login function
        boolean result = Boolean.parseBoolean(userService.loginUser(username, password));

        // if the result is true, the login is successful
        assertFalse(result, "login successful");
    }

    // test the register function
    @Test
    public void testRegister() {
        // prepare test data
        String username = "newUser_for_test";
        String password = "newPassword_for_test";

        // execute the register function
        boolean result = userService.registerUser(username, password, true);

        // if the result is false, the register is successful
        assertFalse(result, "Register successful");
    }
}
