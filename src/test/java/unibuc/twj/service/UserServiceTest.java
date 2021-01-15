package unibuc.twj.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import unibuc.twj.dto.UserDTO;
import unibuc.twj.model.User;
import unibuc.twj.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void userSignupTestHappyFlow() throws Exception
    {
        User user = new User("test1","123456","testEmail@gmail.com","PF");

        User insertedUser = userService.userSignup(user);

        assertNotNull(insertedUser.getUserId());
        assertEquals(user.getEmail(), insertedUser.getEmail(), false);
        assertEquals(user.getUsername(), insertedUser.getUsername(), false);
        assertEquals(user.getTip(), insertedUser.getTip(), false);
    }

    @Test
    public void userSignupTestSadFlow() throws Exception
    {
        User user1 = new User("test1","123456","testEmail@gmail.com","PF");
        userService.userSignup(user1);

        User user2 = new User("test2","123456","testEmail@gmail.com","PF");
        try{
            userService.userSignup(user2);
        }
        catch (Exception e)
        {
            assertEquals("Email-ul nu este unic!", e.getMessage(), false);
        }
    }

    @Test
    public void userLoginTestHappyFlow() throws Exception
    {
        User user = new User("test1","123456","testEmail@gmail.com","PF");

        userService.userSignup(user);
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPassword());
        String result = userService.userLogin(userDTO);

        assertEquals("Succes login!", result, false);
    }

    @Test
    public void userLoginTestSadFlow() throws Exception
    {
        User user = new User("test1","123456","testEmail@gmail.com","PF");
        userService.userSignup(user);

        UserDTO userDTO = new UserDTO(user.getUsername(), "123");
        String result = userService.userLogin(userDTO);

        assertEquals("Username sau parola gresite!", result, false);
    }

}
