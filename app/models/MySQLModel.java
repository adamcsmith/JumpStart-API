package models;

import play.db.ebean.Model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/4/13
 */
@MappedSuperclass
public abstract class MySQLModel extends Model {

    @Id
    public String id;

    @Temporal(TemporalType.DATE)
    public Date created;

    @Temporal(TemporalType.DATE)
    public Date updated;

    /**
     * Inserts new user into db
     *
     * @param user - user to be created
     * @return - created user
     */
    public static User createUser(User user) {

        // make sure username doesn't already exist
        User userCheck = User.find.where().ilike("username", user.username).findUnique();
        if (userCheck != null) {
            throw new RuntimeException("Bummer.  A user with that username already exists.");
        }

        user.save();

        return user;
    }

    /**
     * Retrieves user info from db
     *
     * @param id - id of the user we are searching for
     * @return - retrieved user
     */
    public static User retrieveUser(String id) {

        // user search
        return User.find.byId(Long.parseLong(id));
    }

    /**
     * Updates an existing user in the db
     *
     * @param updatedUser - user with updated fields
     * @param existingUser - user that will be updated
     * @return - updated user
     */
    public static User updateUser(User existingUser, User updatedUser) {

        // check to see if user changed username
        if (!updatedUser.username.equals(existingUser.username)) {
            // make sure new username doesn't already exist
            User userCheck = User.find.where().ilike("username", updatedUser.username).findUnique();
            if (userCheck != null) {
                throw new RuntimeException("Bummer.  A user with that username already exists.");
            }
        }

        updatedUser.created = existingUser.created;
        updatedUser.update();

        return updatedUser;
    }

    /**
     * Removes user from db
     *
     * @param user - user to be deleted
     * @return - deleted user
     */
    public static void deleteUser(User user) {

        user.delete();
    }

}
