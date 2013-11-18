package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import utils.SecurityUtil;

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

        // check to make sure username doesn't already exist
        User userCheck = User.findUserByUsername(user.username);
        if (userCheck != null) {
            return ApiBaseController.badRequest("Bummer.  A user with that username already exists.");
        }

        // create the user
        User createdUser = User.createUser(user);

        // create session
        SecurityUtil.createAuthenticatedSession(createdUser);

        return successfulSaveResult(createdUser.id);
    }

    /**
     * Find a user from an id
     *
     * @param id - user id
     * @return - retrieve result
     */
    public static Result get(String id){

        User user = User.findUserById(id);
        if (user == null) {
            return ApiBaseController.notFound("User with id " + id + " not found");
        }

        // TODO: figure out what we really want to send back to the caller

        return ApiBaseController.ok(Json.toJson(user));
    }

    /**
     * Update a user based off an id
     *
     * @param id - id of user to be updated
     * @return - updated result
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(String id) {

        // find the user to be updated
        User existingUser = User.findUserById(id);
        if (existingUser == null) {
            return ApiBaseController.notFound("User with id " + id + " not found");
        }

        JsonNode json = request().body().asJson();

        ObjectMapper om = new ObjectMapper();

        // grab the updated user fields
        User updatedUser = om.convertValue(json, User.class);
        updatedUser.id = id;
        updatedUser.created = existingUser.created;
        updatedUser.updated = new Date();

        // check for username change
        if (!updatedUser.username.equals(existingUser.username)) {
            // check to make sure username doesn't already exist
            User userCheck = User.findUserByUsername(updatedUser.username);
            if (userCheck != null) {
                return ApiBaseController.badRequest("Bummer.  A user with that username already exists.");
            }
        }

        // update the user
        User.updateUser(updatedUser);

        return successfulSaveResult(updatedUser.id);
    }

    /**
     * Delete a user based off id
     *
     * @param id - user id to be deleted
     * @return - delete result
     */
    public static Result delete(String id) {

        User user = User.findUserById(id);
        if (user == null) {
            return ApiBaseController.notFound("User with id " + id + " not found.  Delete failed.");
        } else {
            User.deleteUser(user);
            return ApiBaseController.ok("Woohoo! User " + user.username + " successfully deleted");
        }
    }

}
