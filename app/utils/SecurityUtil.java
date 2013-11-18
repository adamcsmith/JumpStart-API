package utils;

/**
 * Created with IntelliJ IDEA for jumpstart
 * User: phutchinson
 * Date: 3/18/13
 * Time: 2:52 PM
 */

import models.User;
import play.mvc.Http;

/**
 * Utility class for handling all things security related within the application.
 */
public class SecurityUtil {

    private static final String SESSION_USER_IDENTIFIER = "authenticated_user_id";

    /**
     * Retrieves the actual User object that is currently logged in to the system which may be null
     * @return User
     */
    public static User connectedUser(){
        Http.Session session = Http.Context.current().session();
        String userId = session != null ? session.get(SESSION_USER_IDENTIFIER) : null;
        if(userId != null){
            return User.findUserById(userId);
        }

        return null;
    }

    /**
     * Sets all the appropriate values in the current session
     * @param user
     */
    public static void createAuthenticatedSession(User user){
        Http.Session session = Http.Context.current().session();
        session.put(SESSION_USER_IDENTIFIER, user.id.toString());
    }

    /**
     * Clears the current Session including all security credentials which is effectively logging the user out of the
     * application
     */
    public static void clearSession(){
        Http.Session session = Http.Context.current().session();
        session.remove(SESSION_USER_IDENTIFIER);
    }
}
