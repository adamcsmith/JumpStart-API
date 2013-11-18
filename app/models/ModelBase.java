package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import helpers.MongoHelper;
import org.bson.types.ObjectId;
import play.Play;
import play.db.ebean.Model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/18/13
 */
@MappedSuperclass
public class ModelBase extends Model{

    // pulls data from application.conf and sets local variables
    private static final String DBTYPE = Play.application().configuration().getString("jumpstart.dbtype");
    private static final String MONGO = "mongo";
    private static final String MYSQL = "mysql";

    @Id
    public String id;

    @Temporal(TemporalType.DATE)
    public Date created;

    @Temporal(TemporalType.DATE)
    public Date updated;

    /**
     *
     * @param object
     * @param collectionName
     * @return
     */
    public static Object create(Object object, String collectionName) {

        if (DBTYPE.equals(MONGO)) {

            DBObject dbObject = (DBObject) object;

            DBCollection collection = MongoHelper.getDBCollection(collectionName);
            collection.save(dbObject);

            return dbObject;

        } else if (DBTYPE.equals(MYSQL)) {

            ((ModelBase) object).save();
            return object;

        } else {

            throw new RuntimeException("Kaboom");
        }
    }

    /**
     *
     * @param object
     * @param collectionName
     * @return
     */
    public static Object updateObject(Object object, String collectionName) {

        if (DBTYPE.equals(MONGO)) {

            DBObject updatedObject = (DBObject) object;

            DBCollection collection = MongoHelper.getDBCollection(collectionName);

            // Lookup existing object
            DBObject existingObject = collection.findOne(new BasicDBObject().append("_id", new ObjectId(updatedObject.get("_id").toString())));

            collection.update(existingObject, updatedObject);

            return updatedObject;

        } else if (DBTYPE.equals(MYSQL)) {

            ((ModelBase) object).update();
            return object;

        } else {

            throw new RuntimeException("Kaboom");
        }
    }


    /**
     *
     * @param object
     * @param collectionName
     */
    public static void delete(Object object, String collectionName) {

        if (DBTYPE.equals(MONGO)) {

            // map object onto dbobject so we can get _id off the dbobject
            DBObject dbObject = (DBObject) object;

            // create collection to search from
            DBCollection collection = MongoHelper.getDBCollection(collectionName);

            // Lookup object
            DBObject userResult = collection.findOne(new BasicDBObject().append("_id", new ObjectId(dbObject.get("_id").toString())));

            collection.remove(userResult);

        } else if (DBTYPE.equals(MYSQL)) {

            ((ModelBase) object).delete();

        } else {

            throw new RuntimeException("Kaboom");
        }

    }

}
