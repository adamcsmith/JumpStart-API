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
 * Created with IntelliJ IDEA for jumpstart
 * User: phutchinson
 * Date: 3/18/13
 * Time: 9:24 PM
 */
public class Sessions extends ApiBaseController {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create(){
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").asText();
        String password = json.findPath("password").asText();
        User user = null;
        try {
            user = User.connect(username, password);
        } catch (RuntimeException e) {
            Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
            errors.put("create", Arrays.asList(new ValidationError("user", e.getMessage())));
            return errorResult(errors);
        }

        SecurityUtil.createAuthenticatedSession(user);
        return successfulSaveResult(user);

    }

    public static Result delete(){
        SecurityUtil.clearSession();
        return ok();
    }
}
