package models;

import com.mongodb.*;
import org.bson.types.ObjectId;
import play.Play;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Result;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.net.UnknownHostException;
import java.util.Date;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/5/13
 */

// TODO: only extending Model to appease errors - need a better fix
@MappedSuperclass
public abstract class MongoModel extends Model {

    // pulls data from application.conf and sets local variables
    private static final String dbName = Play.application().configuration().getString("mongo.dbName");
    private static final String collectionName = Play.application().configuration().getString("mongo.userCollectionName");

    @Id
    public String id;

    @Temporal(TemporalType.DATE)
    public Date created;

    @Temporal(TemporalType.DATE)
    public Date updated;

    /**
     * Inserts new user into db
     *
     * @param user - user to be created
     * @return
     */
    public static Result createUser(User user) {

        DBCollection collection = configureMongoClient();

        // maps fields from user object onto db object
        BasicDBObject dbObject = createDBObjectFromUser(user);

        // make sure credentials don't already exist
        DBObject userResult = collection.findOne(new BasicDBObject().append("username", user.username));
        if (userResult != null) {
            return badRequest("User with name " + user.username + "already exists.");
        }

        // save the new user
        collection.save(dbObject);

        //populate a user so we can return to the caller
        User createdUser = populateUser(dbObject);

        return ok(Json.toJson(createdUser));
    }

    /**
     * Retrieves user info from db
     *
     * @param id - id of the user we are searching for
     * @return
     */
    public static Result retrieveUser(String id) {

        DBCollection collection = configureMongoClient();

        // search for user
        DBObject userResult = collection.findOne(new BasicDBObject().append("_id", new ObjectId(id)));

        // return 404 if not found
        if (userResult == null) {
            return notFound("User not found with id " + id);
        }

        // create and populate user object based on db object returned
        User user = populateUser(userResult);

        return ok(Json.toJson(user));
    }

    /**
     * Updates an existing user in the db
     *
     * @param updatedUser - user with updated fields
     * @param existingUserID - id of the user we will be updating
     * @return
     */
    public static Result updateUser(User updatedUser, String existingUserID) {

        DBCollection collection = configureMongoClient();

        // search for user
        DBObject userResult = collection.findOne(new BasicDBObject().append("_id", new ObjectId(existingUserID)));

        // return 404 if not found
        if (userResult == null) {
            return notFound("User not found with id " + existingUserID);
        }

        // maps fields from user object onto db object
        BasicDBObject dbObject = createDBObjectFromUser(updatedUser);

        // update the user in the db
        collection.update(userResult, dbObject);

        //populate a user so we can return to the caller
        User user = populateUser(dbObject);

        return ok(Json.toJson(user));
    }

    /**
     * Removes user from db
     *
     * @param id - id of the user to be deleted
     * @return
     */
    public static Result deleteUser(String id) {

        DBCollection collection = configureMongoClient();

        // find our database object to delete
        DBObject userResult = collection.findOne(new BasicDBObject().append("_id", new ObjectId(id)));

        // delete user
        collection.remove(userResult);

        return ok("User successfully deleted.");
    }

    /**
     * Configure mongo client by setting db name and table name
     *
     * @return
     */
    private static DBCollection configureMongoClient() {

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        DB db = mongoClient.getDB(dbName);

        return db.getCollection(collectionName);
    }

    /**
     * Builds a database object based on the fields the passed in User has
     *
     * @param user - user to map the fields from
     * @return
     */
    private static BasicDBObject createDBObjectFromUser(User user) {

        BasicDBObject dbObject = new BasicDBObject();

        dbObject.append("username", user.username);
        dbObject.append("password", user.password);
        dbObject.append("temporaryPassword", user.temporaryPassword);
        dbObject.append("temporaryPasswordExpiration", user.temporaryPasswordExpiration);
        dbObject.append("lastLogin", user.lastLogin);
        dbObject.append("failedLoginAttempts", user.failedLoginAttempts);
        dbObject.append("created", user.created);
        dbObject.append("updated", user.updated);

        return dbObject;
    }

    /**
     * populates a User object based on the fields of a DBObject
     *
     * @param dbObject - object with fields we want mapped to a User
     * @return
     */
    private static User populateUser(DBObject dbObject) {

        User user = new User();

        user.id = dbObject.get("_id").toString();
        user.username = dbObject.get("username").toString();
        user.password = dbObject.get("password").toString();
        // TODO: only throwing NPE on this one.....why?
//        user.temporaryPassword = dbObject.get("temporaryPassword").toString();
        user.temporaryPasswordExpiration = (Date) dbObject.get("temporaryPasswordExpiration");
        user.lastLogin = (Date) dbObject.get("lastLogin");
        user.failedLoginAttempts = (Integer) dbObject.get("failedLoginAttempts");
        user.created = (Date) dbObject.get("created");
        user.updated = (Date) dbObject.get("updated");

        return user;
    }
}
