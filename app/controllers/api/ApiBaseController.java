package controllers.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Results;

/**
 * Created with IntelliJ IDEA
 * User: adamcsmith
 * Date: 3/18/13
 */

/**
 * Base class for API-type controllers
 */
public class ApiBaseController extends Controller {

    public static Results.Status badRequest(String msg) {

        ObjectNode result = Json.newObject();
        result.put("badRequest", msg);

        return badRequest(result);
    }

    public static Results.Status notFound(String msg) {

        ObjectNode result = Json.newObject();
        result.put("notFound", msg);

        return notFound(result);
    }

    public static Results.Status ok(String msg) {

        ObjectNode result = Json.newObject();
        result.put("ok", msg);

        return ok(result);
    }

    public static Results.Status forbidden(String msg) {

        ObjectNode result = Json.newObject();
        result.put("forbidden", msg);

        return forbidden(result);
    }

    public static Results.Status internalServerError(String msg) {

        ObjectNode result = Json.newObject();
        result.put("internal server error", msg);

        return internalServerError(result);
    }

}
