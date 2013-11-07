package controllers.api;

import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;

import java.util.List;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA for jumpstart
 * User: phutchinson
 * Date: 3/16/13
 * Time: 11:14 PM
 */
public class Users extends ApiBaseController {

    /**
     * The below CRUD operations currently work for the in-memory database as well
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
            return User.createUser();
        }
    }


    public static Result retrieve(String id){

        return User.retrieveUser(id);
    }

//    public static Result retrieve(String id){
//
//        User user = null;
//
//        try {
//            user = User.retrieveUser(id);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        return ok(Json.toJson(user));
//    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(String id) {

        Form<User> userForm = form(User.class);
        userForm = userForm.bind(request().body().asJson());

        User updatedUser  = userForm.get();

        return User.updateUser(updatedUser, id);
    }


    public static Result delete(String id) {

        return User.deleteUser(id);
    }

    // method is strictly for testing purposes
    public static Result findAllUsers() {

        List<User> userList = User.find.all();
        return ok(Json.toJson(userList));
    }

}
