import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.user.MySqlUserService;
import service.user.UserService;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/26/13
 */
public class UserServiceTest {

    private static UserService userService = new MySqlUserService();
//    private static UserService userService = new MongoUserService();

    @Before
    public void setUp() throws Exception {
        deleteAllUsers();
    }

    @After
    public void tearDown() throws Exception {
        deleteAllUsers();
    }

    private void deleteAllUsers() {

        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                for ( User user : userService.findAllUsers()) {
                    userService.deleteUser(user);
                }
            }
        });
    }

    @Test
    public void testSavingUser() throws Exception {

        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                User user = new User();
                user.username = "test.test@gmail.com";
                user.password = "password";
                user.failedLoginAttempts = 0;
                user.created = new Date();
                user = userService.createUser(user);

                User readUser = userService.findUserById(user.id);
                assertThat(readUser != null);
                assertThat(readUser.id != null);
                assertThat(readUser.failedLoginAttempts == 0);
                assertThat(readUser.password == "password");
                assertThat(readUser.username.equals("test.test@gmail.com"));
                assertThat(readUser.created != null);

                readUser.username = "test.osterone@aol.com";
                readUser.updated = new Date();
                readUser = userService.updateUser(readUser);

                assertThat(readUser.created != null);
                assertThat(readUser.updated != null);
                assertThat(readUser.created.before(readUser.updated));
            }

        });

    }
}
