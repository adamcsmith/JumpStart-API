package controllers.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: adamcsmith
 * Date: 10/31/13
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

    /***********************************************************************
     * Protected Utility Methods                                           *
     ***********************************************************************/

    /**
     * Builds an ok (http 200) response with data and error fields constructed appropriately
     * @param errorMap Map of Validation errors to put into the error data element if any exist
     * @return Result
     */
    protected static Result errorResult(Map<String, List<ValidationError>> errorMap){

        List<String> errorMessages = new ArrayList<String>();
        Map<String, Object> result = new HashMap<String, Object>();
        if(errorMap != null && !errorMap.isEmpty()){

            for (List<ValidationError> errorList : errorMap.values()) {
                for (ValidationError validationError : errorList) {
                    errorMessages.add(errorMessage(validationError));
                }
            }

            result.put("error", errorMessages);
        }

        return ok(Json.toJson(result));
    }

    protected static Result successfulSaveResult(String id){
        Map<String, Object> result = new HashMap<String, Object>();
        Map <String, Object> innerData = new HashMap<String, Object>();
        innerData.put("id", id);
        result.put("data", innerData);

        return ok(Json.toJson(result));
    }

    /***********************************************************************
     * Private Utility Methods                                             *
     ***********************************************************************/

    /**
     * Creates a message from a ValidationError this logic is "borrowed" from how Play v1.2.5 displays error messages
     * since Play 2.1 ValidationErrors don't have the same display capabilities
     * @param error ValidationError to generate message for
     * @return String a display-able error message
     */
    private static String errorMessage(ValidationError error) {
        String printableKey = Messages.get(error.key());
        List<Object> args = new ArrayList<Object>();
        args.add(printableKey);
        args.addAll(error.arguments());

        return Messages.get(error.message(), args.toArray(new Object[]{args.size()}));
    }

}
