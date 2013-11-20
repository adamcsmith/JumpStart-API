package service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public abstract class MongoBaseService {

    /**
     * Inserts new objects into mongo db
     *
     * @param object
     * @param collection
     * @return created object
     */
    public Object create(Object object, DBCollection collection) {

        DBObject dbObject = (DBObject) object;
        collection.save(dbObject);
        return dbObject;
    }

    /**
     * Updates an existing object in the mongo db
     *
     * @param object
     * @param collection
     * @return updated object
     */
    public Object updateObject(Object object, DBCollection collection) {

        DBObject updatedObject = (DBObject) object;

        // Lookup existing object
        String objectId = updatedObject.get("_id").toString();
        DBObject existingObject = collection.findOne(new BasicDBObject("_id", new ObjectId(objectId)));

        collection.update(existingObject, updatedObject);

        return updatedObject;
    }

    /**
     * Remove existing object from db
     *
     * @param object
     * @param collection
     */
    public void delete(Object object, DBCollection collection) {

        // Lookup object
        String objectId = ((DBObject) object).get("_id").toString();
        DBObject userResult = collection.findOne(new BasicDBObject("_id", new ObjectId(objectId)));

        collection.remove(userResult);
    }
}
