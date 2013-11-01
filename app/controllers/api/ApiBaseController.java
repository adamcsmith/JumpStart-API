package controllers.api;

import models.ModelBase;
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
 * Created with IntelliJ IDEA for jumpstart
 * User: phutchinson
 * Date: 3/18/13
 * Time: 12:24 PM
 */

/**
 * Base class for API-type controllers
 */
public class ApiBaseController extends Controller {

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

    protected static Result successfulSaveResult(ModelBase model){
        Map <String, Object> result = new HashMap<String, Object>();
        Map <String, Object> innerData = new HashMap<String, Object>();
        innerData.put("id", model.id);
        result.put("data", innerData);

        return ok(Json.toJson(result));
    }

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
