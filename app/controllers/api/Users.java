package controllers.api;

import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import utils.SecurityUtil;

import java.util.Date;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA for jumpstart
 * User: phutchinson
 * Date: 3/16/13
 * Time: 11:14 PM
 */
public class Users extends ApiBaseController {

    /**
     * The below CRUD operations currently work for the in memory database as well
     * as a MySQL database.  Currently working on testing with mongodb.  Think mongo
     * will force us to abstract these out more.
     */


    @BodyParser.Of(BodyParser.Json.class)
    public static Result create(){

        Form<User> userForm = form(User.class);
        userForm = userForm.bind(request().body().asJson());

        if(userForm.hasErrors()){
            return errorResult(userForm.errors());
        } else {
            User user = userForm.get();
            user.created = new Date();
            user.save();
            // TODO: strip out session logic
            SecurityUtil.createAuthenticatedSession(user);
            return ok(Json.toJson(user));
        }
    }


    public static Result retrieve(Long id){

        User user = User.findByID(id);

        if (user == null) {
            return notFound("User with id " + id + "not found");
        } else {
            return ok(Json.toJson(user));
        }
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {

        Form<User> userForm = form(User.class);
        userForm = userForm.bind(request().body().asJson());

        User updatedUser  = userForm.get();
        updatedUser.setId(id);

        User existingUser = User.findByID(id);
        if (existingUser == null) {
            return notFound("User with id " + id + "not found");
        }

        updatedUser.created = existingUser.created;
        updatedUser.updated = new Date();
        updatedUser.update();

        return ok(Json.toJson(updatedUser));
    }


    public static Result delete(Long id) {

        User user = User.findByID(id);

        if (user == null) {
            return notFound("User not found for id " + id);
        }

        user.delete();
        return ok("User with id " + id + " successfully deleted!");
    }

}
