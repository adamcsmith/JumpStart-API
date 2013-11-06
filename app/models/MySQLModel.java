package models;

import play.db.ebean.Model;
import utils.SecurityUtil;

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



    // TODO: consider returning Results instead of User objects
    // TODO: when these crud operations are vetted, delete ModelBase.java class

    public static User createUser(User user) {

        user.created = new Date();
        user.save();
        // TODO: strip out session logic
        SecurityUtil.createAuthenticatedSession(user);

        return user;
    }

    public static User retrieveUser(String id) {

        User user = User.find.byId(Long.parseLong(id));

//        if (user == null) {
//            throw new RuntimeException("Attempting to retrieve user but user not found");
//        }

        return user;
    }

    public static User updateUser(User updatedUser, String existingUserID) {

        updatedUser.id = existingUserID;

        User existingUser = User.retrieveUser(existingUserID);
        if (existingUser == null) {
            throw new RuntimeException("User with id " + existingUserID + "not found. Update failed.");
        }

        updatedUser.created = existingUser.created;
        updatedUser.updated = new Date();
        updatedUser.update();

        return updatedUser;
    }

    public static void deleteUser(String id) {

        User user = retrieveUser(id);

        if (user == null) {
            throw new RuntimeException("User delete failed.  User not found");
//            return notFound("user not found");
        } else {
            user.delete();
        }
    }

}
