package controllers.api;


import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import utils.SecurityUtil;

import java.util.Date;
import java.util.List;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA for jumpstart
 * User: phutchinson
 * Date: 3/16/13
 * Time: 11:14 PM
 */
public class Users extends ApiBaseController {

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
            SecurityUtil.createAuthenticatedSession(user);
            return successfulSaveResult(user);
        }
    }

    public static Result get(Long id){

        User user = User.find.byId(id);
        if (user == null) {
            return notFound("User not found");
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

        User existingUser = User.find.byId(id);
        if (existingUser == null) {
            return notFound("User not found");
        }

        updatedUser.created = existingUser.created;
        updatedUser.updated = new Date();
        updatedUser.update();

        return ok(Json.toJson(updatedUser));
    }


    public static Result delete(Long id) {

        User user = User.find.byId(id);

        // probably could just use this but won't return a result
        // User.find.ref(id).delete();

        if (user == null) {
            return notFound("User not found");
        }

        user.delete();
        return ok();
    }

}
