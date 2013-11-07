package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;

import java.util.Date;
import java.util.List;

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

        JsonNode json = request().body().asJson();

        ObjectMapper om = new ObjectMapper();

        User user = om.convertValue(json, User.class);
        user.created = new Date();

        return User.createUser(user);
    }


    public static Result retrieve(String id){

        return User.retrieveUser(id);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(String id) {

        JsonNode json = request().body().asJson();

        ObjectMapper om = new ObjectMapper();

        User updatedUser = om.convertValue(json, User.class);
        updatedUser.id = id;
        updatedUser.updated = new Date();

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
