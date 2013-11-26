import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.user.UserService;
import utils.ServiceUtil;

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

    @Before
    public void setUp() throws Exception {

        deleteAllUsers();
    }

    @After
    public void tearDown() throws Exception {
        deleteAllUsers();
    }

    private void deleteAllUsers() {

        // TODO: add check to make sure using test database

        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                UserService userService = ServiceUtil.getDBUserService();
                for ( User user : userService.findAllUsers()) {
                    userService.deleteUser(user);
                }
            }
        });
    }

    @Test
    public void testUserCRUD() throws Exception {

        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                UserService userService = ServiceUtil.getDBUserService();

                User user = new User();
                user.username = "test.test@gmail.com";
                user.password = "password";
                user.failedLoginAttempts = 0;
                user.created = new Date();
                user = userService.createUser(user);   // test create

                User readUser = userService.findUserById(user.id);  // test get
                assertThat(readUser != null);
                assertThat(readUser.id != null);
                assertThat(readUser.failedLoginAttempts == 0);
                assertThat(readUser.password == "password");
                assertThat(readUser.username.equals("test.test@gmail.com"));
                assertThat(readUser.created != null);

                readUser.username = "test.osterone@aol.com";
                readUser.updated = new Date();
                User updatedUser = userService.updateUser(readUser);  // test update

                assertThat(updatedUser.username = "test.osterone@aol.com");
                assertThat(updatedUser.updated != null);
                assertThat(updatedUser.created.before(readUser.updated));
            }

        });

    }
}
