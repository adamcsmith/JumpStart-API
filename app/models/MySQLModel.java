package models;

import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Result;
import utils.SecurityUtil;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import static controllers.api.ApiBaseController.notFound;
import static play.mvc.Results.ok;

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
     * @return - creation result
     */
    public static Result createUser(User user) {

        user.save();
        // TODO: strip out session logic
        SecurityUtil.createAuthenticatedSession(user);

        return ok(Json.toJson(user));
    }

    /**
     * Retrieves user info from db
     *
     * @param id - id of the user we are searching for
     * @return - retrieve result
     */
    public static Result retrieveUser(String id) {

        // user search
        User user = User.find.byId(Long.parseLong(id));

        if (user == null) {
            return notFound("User with id " + id + "not found");
        } else {
            return ok(Json.toJson(user));
        }
    }

    /**
     * Updates an existing user in the db
     *
     * @param updatedUser - user with updated fields
     * @param existingUserID - id of the user we will be updating
     * @return - update result
     */
    public static Result updateUser(User updatedUser, String existingUserID) {

        User existingUser = User.find.byId(Long.parseLong(existingUserID));
        if (existingUser == null) {
           return notFound("User with id " + existingUserID + "not found. Update failed.");
        }

        updatedUser.created = existingUser.created;
        updatedUser.update();

        return ok(Json.toJson(updatedUser));
    }

    /**
     * Removes user from db
     *
     * @param id - id of the user to be deleted
     * @return - delete result
     */
    public static Result deleteUser(String id) {

        // user search
        User user = User.find.byId(Long.parseLong(id));

        if (user == null) {
            return notFound("User with id " + id + "not found.  Delete failed.");
        } else {
            user.delete();
            return ok("Woohoo!  User with id " + id + "successfully deleted!");
        }
    }

}
