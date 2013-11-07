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


    public static Result createUser(User user) {

        user.created = new Date();
        user.save();
        // TODO: strip out session logic
        SecurityUtil.createAuthenticatedSession(user);

        return ok(Json.toJson(user));
    }

    public static Result retrieveUser(String id) {

        User user = User.find.byId(Long.parseLong(id));

        if (user == null) {
            return notFound("User with id " + id + "not found");
        } else {
            return ok(Json.toJson(user));
        }
    }

    public static Result updateUser(User updatedUser, String existingUserID) {

        updatedUser.id = existingUserID;

        User existingUser = User.find.byId(Long.parseLong(existingUserID));
        if (existingUser == null) {
           return notFound("User with id " + existingUserID + "not found. Update failed.");
        }

        updatedUser.created = existingUser.created;
        updatedUser.updated = new Date();
        updatedUser.update();

        return ok(Json.toJson(updatedUser));
    }

    public static Result deleteUser(String id) {

        User user = User.find.byId(Long.parseLong(id));

        if (user == null) {
            return notFound("User with id " + id + "not found.  Delete failed.");
        } else {
            user.delete();
            return ok("Woohoo!  User with id " + id + "successfully deleted!");
        }
    }

}
