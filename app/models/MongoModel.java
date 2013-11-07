package models;

import com.mongodb.*;
import org.bson.types.ObjectId;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Result;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.net.UnknownHostException;
import java.util.Date;

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

    @Id
    public String id;

    @Temporal(TemporalType.DATE)
    public Date created;

    @Temporal(TemporalType.DATE)
    public Date updated;

    public static Result createUser(User user) {

        DBCollection collection = configureMongoClient();

        BasicDBObject test = new BasicDBObject();
        test.append("username", user.username);
        test.append("password", user.password);

        collection.save(test);

        return ok();
    }

    public static Result retrieveUser(String id) {

        DBCollection collection = configureMongoClient();

        // search for user
        DBObject userResult = collection.findOne(new BasicDBObject().append("_id", new ObjectId(id)));

        // return 404 if not found
        if (userResult == null) {
            return notFound("User not found with id " + id);
        }

        // create and populate user object based on db object returned
        User user = new User();
        user.id = userResult.get("_id").toString();
        user.username = userResult.get("username").toString();
        user.password = userResult.get("password").toString();

        return ok(Json.toJson(user));
    }

    public static Result updateUser(User updatedUser, String existingUserID) {
        return ok();
    }

    public static Result deleteUser(String id) {

        DBCollection collection = configureMongoClient();

        // find our database object to delete
        DBObject userResult = collection.findOne(new BasicDBObject().append("_id", new ObjectId(id)));

        // delete user
        collection.remove(userResult);

        return ok();
    }

    private static DBCollection configureMongoClient() {

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        DB db = mongoClient.getDB("mydb");

        return db.getCollection("testData");
    }
}
