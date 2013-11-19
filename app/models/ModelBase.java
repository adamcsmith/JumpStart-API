package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
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
public class ModelBase extends Model {

    // pulls data from application.conf and sets local variables
    private static final String DB_TYPE = Play.application().configuration().getString("jumpstart.dbtype");
    private static final String MONGO = "mongo";
    private static final String MYSQL = "mysql";

    @Id
    public String id;

    @Temporal(TemporalType.DATE)
    public Date created;

    @Temporal(TemporalType.DATE)
    public Date updated;

    /**
     * Adds new object to db
     *
     * @param object
     * @param collection
     * @return - created object
     */
    public static Object create(Object object, DBCollection collection) {

        if (DB_TYPE.equals(MONGO)) {

            DBObject dbObject = (DBObject) object;
            collection.save(dbObject);
            return dbObject;

        } else if (DB_TYPE.equals(MYSQL)) {

            ((ModelBase) object).save();
            return object;

        } else {

            throw new RuntimeException("Kaboom");
        }
    }

    /**
     * Update existing object in db
     *
     * @param object
     * @param collection
     * @return - updated object
     */
    public static Object updateObject(Object object, DBCollection collection) {

        if (DB_TYPE.equals(MONGO)) {

            DBObject updatedObject = (DBObject) object;

            // Lookup existing object
            String objectId = updatedObject.get("_id").toString();
            DBObject existingObject = collection.findOne(new BasicDBObject("_id", new ObjectId(objectId)));

            collection.update(existingObject, updatedObject);

            return updatedObject;

        } else if (DB_TYPE.equals(MYSQL)) {

            ((ModelBase) object).update();
            return object;

        } else {

            throw new RuntimeException("Kaboom");
        }
    }

    /**
     * Remove existing object from db
     *
     * @param object
     * @param collection
     */
    public static void delete(Object object, DBCollection collection) {

        if (DB_TYPE.equals(MONGO)) {

            // Lookup object
            String objectId = ((DBObject) object).get("_id").toString();
            DBObject userResult = collection.findOne(new BasicDBObject("_id", new ObjectId(objectId)));

            collection.remove(userResult);

        } else if (DB_TYPE.equals(MYSQL)) {

            ((ModelBase) object).delete();

        } else {

            throw new RuntimeException("Kaboom");
        }

    }

}
