package models;

import com.mongodb.*;
import org.bson.types.ObjectId;
import play.Play;
import play.db.ebean.Model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.net.UnknownHostException;
import java.util.Date;

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
     * @return - creation result
     */
    public static User createUser(User user) {

        DBCollection collection = configureMongoClient();

        // maps fields from user object onto db object
        BasicDBObject dbObject = createDBObjectFromUser(user);

        // make sure credentials don't already exist
        DBObject userResult = collection.findOne(new BasicDBObject().append("username", user.username));
        if (userResult != null) {
            throw new RuntimeException("User with name " + user.username + " already exists.");
        }

        // save the new user
        collection.save(dbObject);

        //populate a user so we can return to the caller
        User createdUser = populateUser(dbObject);

        return createdUser;
    }

    /**
     * Retrieves user info from db
     *
     * @param id - id of the user we are searching for
     * @return - retrieve result
     */
    public static User retrieveUser(String id) {

        DBCollection collection = configureMongoClient();

        // search for user
        DBObject userResult = collection.findOne(new BasicDBObject().append("_id", new ObjectId(id)));

        // create and populate user object based on db object returned
        User user = populateUser(userResult);

        return user;
    }

    /**
     * Updates an existing user in the db
     *
     * @param updatedUser - user with updated fields
     * @param existingUser - user we will be updating
     * @return - update result
     */
    public static User updateUser(User existingUser, User updatedUser) {

        DBCollection collection = configureMongoClient();

        // check to make sure that user did not try and change username to one that already exists
        if (!existingUser.username.equals(updatedUser.username)) {

            DBObject userNameCheck = collection.findOne(new BasicDBObject().append("username", updatedUser.username));
            if (userNameCheck != null) {
                throw new RuntimeException("User with name " + updatedUser.username + "already exists.");
            }
        }

        // maps fields from user object onto db object
        BasicDBObject updatedDbObject = createDBObjectFromUser(updatedUser);
        DBObject existingDbObject = createDBObjectFromUser(existingUser);

        // update the user in the db
        collection.update(existingDbObject, updatedDbObject);

        //populate a user so we can return to the caller
        User user = populateUser(updatedDbObject);

        return user;
    }

    /**
     * Removes user from db
     *
     * @param user - user to be deleted
     * @return - delete result
     */
    public static void deleteUser(User user) {

        DBCollection collection = configureMongoClient();

        // find our database object to delete
        DBObject userResult = collection.findOne(new BasicDBObject().append("_id", new ObjectId(user.id)));

        // delete user
        collection.remove(userResult);
    }

    /**
     * Configure mongo client by setting db name and table name
     *
     * @return - configured dbcollection
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
     * @return - populated dbobject
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
     * @return - populated user object
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
