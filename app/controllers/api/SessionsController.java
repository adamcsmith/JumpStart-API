package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import play.data.validation.ValidationError;
import play.mvc.BodyParser;
import play.mvc.Result;
import utils.SecurityUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/12/13
 */
public class SessionsController extends ApiBaseController {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create(){
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").asText();
        String password = json.findPath("password").asText();
        User user;
        try {
            user = User.connect(username, password);
        } catch (RuntimeException e) {
            Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
            errors.put("create", Arrays.asList(new ValidationError("user", e.getMessage())));
            return errorResult(errors);
        }

        SecurityUtil.createAuthenticatedSession(user);
        return successfulSaveResult(user.id);
    }

    public static Result delete(){
        SecurityUtil.clearSession();
        return ok();
    }
}
