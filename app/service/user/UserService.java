package service.user;

import models.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public interface UserService {

    public abstract User findUser(String id, String username);

    public abstract User createUser(User user);

    public abstract User updateUser(User user);

    public abstract void deleteUser(User user);

    public abstract List<User> findAllUsers();
}

