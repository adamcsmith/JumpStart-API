package utils;

import play.Play;
import service.user.MongoUserService;
import service.user.MySqlUserService;
import service.user.UserService;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public class ServiceUtil {

    private static final String DB_TYPE = Play.application().configuration().getString("jumpstart.dbtype");

    private static final String MONGO = "mongo";
    private static final String MYSQL = "mysql";

    /**
     * Figure out which db service to use
     *
     * @return correct service
     */
    public static UserService getDBUserService() {

        //read in env variable and instantiate correct user
        if (DB_TYPE.equals(MONGO)) {
            return new MongoUserService();
        } else if (DB_TYPE.equals(MYSQL)) {
            return new MySqlUserService();
        } else {
            throw new RuntimeException("Can't find the database type.  Check the application.conf file " +
                    "for the db.default.type setting.");
        }

    }
}
