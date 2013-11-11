package models;

import controllers.api.ApiBaseController;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Result;

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
     * @return - creation result
     */
    public static Result createUser(User user) {

        // make sure username doesn't already exist
        User userCheck = User.find.where().ilike("username", user.username).findUnique();
        if (userCheck != null) {
            return ApiBaseController.badRequest("Bummer.  A user with that username already exists.");
        }

        user.save();

        return ApiBaseController.ok(Json.toJson(user));
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
            return ApiBaseController.notFound("User with id " + id + " not found");
        } else {
            return ApiBaseController.ok(Json.toJson(user));
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

        // find the user to be updated
        User existingUser = User.find.byId(Long.parseLong(existingUserID));
        if (existingUser == null) {
           return ApiBaseController.notFound("User with id " + existingUserID + " not found. Update failed.");
        }

        // check to see if user changed username
        if (!updatedUser.username.equals(existingUser.username)) {
            // make sure new username doesn't already exist
            User userCheck = User.find.where().ilike("username", updatedUser.username).findUnique();
            if (userCheck != null) {
                return ApiBaseController.badRequest("Bummer.  A user with that username already exists.");
            }
        }

        updatedUser.created = existingUser.created;
        updatedUser.update();

        return ApiBaseController.ok(Json.toJson(updatedUser));
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
            return ApiBaseController.notFound("User with id " + id + " not found.  Delete failed.");
        }

        user.delete();

        return ApiBaseController.ok("Woohoo! User " + user.username + " successfully deleted");
    }

}
