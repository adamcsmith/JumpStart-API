package controllers.api;

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

    protected static String errorDetail(String msg) {
        return "{\"status\": \"error\", \"reason\": \"" + msg + "\"}";
    }

    public static Results.Status badRequest(String msg) {
        return Controller.badRequest(errorDetail(msg));
    }

    public static Results.Status notFound(String msg) {
        return Controller.notFound(errorDetail(msg));
    }

    public static Results.Status forbidden(String msg) {
        return Controller.forbidden(errorDetail(msg));
    }

    public static Results.Status internalServerError(String msg) {
        return Controller.internalServerError(errorDetail(msg));
    }

}
