package service;

import com.mongodb.DBCollection;
import models.ModelBase;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public abstract class MySqlBaseService {

    /**
     * Adds new object to db
     *
     * @param object
     * @param collection
     * @return - created object
     */
    public Object create(Object object, DBCollection collection) {

        ((ModelBase) object).save();
        return object;
    }

    /**
     * Update existing object in db
     *
     * @param object
     * @param collection
     * @return - updated object
     */
    public Object updateObject(Object object, DBCollection collection) {

        ((ModelBase) object).update();
        return object;
    }

    /**
     * Remove existing object from db
     *
     * @param object
     * @param collection
     */
    public void delete(Object object, DBCollection collection) {

        ((ModelBase) object).delete();
    }
}
