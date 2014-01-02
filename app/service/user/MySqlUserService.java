package service.user;

import models.User;
import org.apache.commons.lang3.StringUtils;
import service.MySqlBaseService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public class MySqlUserService extends MySqlBaseService implements UserService {


    /***********************************************************************
     * User CRUD methods - MySql syntax                                    *
     ***********************************************************************/

    @Override
    public User findUser(String id, String username) {

        User user = null;

        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(username)) {

            // Figure out how to query multiple fields in mysql
            user =  User.find.where().ieq("username", username).ieq("id", id).findUnique();

        } else {

            if (StringUtils.isNotBlank(id)){
                user =  User.find.byId(Long.parseLong(id));
            }

            if (StringUtils.isNotBlank(username)){
                user =  User.find.where().ieq("username", username).findUnique();
            }

        }

        return user;
    }


    @Override
    public User createUser(User user) {

        return (User) super.create(user, null);
    }

    @Override
    public User updateUser(User user) {

        return (User) super.updateObject(user, null);
    }

    @Override
    public void deleteUser(User user) {

        super.delete(user, null);
    }

    @Override
    public List<User> findAllUsers() {

        return User.find.all();
    }
}
