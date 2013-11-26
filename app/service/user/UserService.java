package service.user;

import models.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public interface UserService {

    public abstract User findUserByUsername(String username);

    public abstract User findUserById(String id);

    public abstract User createUser(User user);

    public abstract User updateUser(User user);

    public abstract void deleteUser(User user);

    public abstract List<User> findAllUsers();
}

