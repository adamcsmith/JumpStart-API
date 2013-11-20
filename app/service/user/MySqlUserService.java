package service.user;

import models.User;
import service.MySqlBaseService;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public class MySqlUserService extends MySqlBaseService implements UserService {

    @Override
    public User findUserByUsername(String username) {
        return User.find.where().ieq("username", username).findUnique();
    }

    @Override
    public User findUserById(String id) {
        return User.find.byId(Long.parseLong(id));
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
}