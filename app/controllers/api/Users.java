package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import play.mvc.BodyParser;
import play.mvc.Result;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * User: adamcsmith
 * Date: 10/31/13
 */
public class Users extends ApiBaseController {

    /**
     * Create user
     *
     * @return - creation result
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create(){

        JsonNode json = request().body().asJson();

        ObjectMapper om = new ObjectMapper();

        User user = om.convertValue(json, User.class);
        user.created = new Date();

        return User.createUser(user);
    }

    /**
     * Find a user from an id
     *
     * @param id - user id
     * @return - retrieve result
     */
    public static Result retrieve(String id){

        return User.retrieveUser(id);
    }

    /**
     * Update a user
     *
     * @param id - id of user to be updated
     * @return - updated result
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(String id) {

        JsonNode json = request().body().asJson();

        ObjectMapper om = new ObjectMapper();

        User updatedUser = om.convertValue(json, User.class);
        updatedUser.id = id;
        updatedUser.updated = new Date();

        return User.updateUser(updatedUser, id);
    }

    /**
     * Delete a user based off id
     *
     * @param id - user id to be deleted
     * @return - delete result
     */
    public static Result delete(String id) {

        return User.deleteUser(id);
    }

}
