package service.user;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import models.User;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import play.Play;
import service.MongoBaseService;
import utils.MongoUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public class MongoUserService extends MongoBaseService implements UserService {

    private static final String MONGO_USER_COLL = Play.application().configuration().getString("mongo.userCollectionName");

    /***********************************************************************
     * User CRUD methods - Mongo syntax                                    *
     ***********************************************************************/

    public User findUser(String id, String username) {

        BasicDBObject dbObject;
        DBObject result;

        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(username)) {

            dbObject = new BasicDBObject();
            dbObject.append("_id", new ObjectId(id));
            dbObject.append("username", username);

        } else {

            BasicDBList or = new BasicDBList();

            DBObject clauseId;
            if (StringUtils.isNotBlank(id)){
                clauseId = new BasicDBObject("_id", new ObjectId(id));
                or.add(clauseId);
            }

            DBObject clauseUserName;
            if (StringUtils.isNotBlank(username)){
                clauseUserName = new BasicDBObject("username", username);
                or.add(clauseUserName);
            }

            dbObject = new BasicDBObject("$or", or);
        }

        result = MongoUtil.getDBCollection(MONGO_USER_COLL).findOne(dbObject);
        if (result != null) {
            return populateUser(result);
        } else {
            return null;
        }

    }

    @Override
    public User createUser(User user) {

        BasicDBObject dbObject = createDBObjectFromUser(user);
        DBObject createdDBObject = (DBObject) super.create(dbObject, MongoUtil.getDBCollection(MONGO_USER_COLL));

        return populateUser(createdDBObject);
    }

    @Override
    public User updateUser(User user) {

        BasicDBObject dbObject = createDBObjectFromUser(user);
        DBObject updatedObject = (DBObject) super.updateObject(dbObject, MongoUtil.getDBCollection(MONGO_USER_COLL));
        User updatedUser = populateUser(updatedObject);
        updatedUser.updated = new Date();

        return updatedUser;
    }

    @Override
    public void deleteUser(User user) {

        BasicDBObject dbObject = createDBObjectFromUser(user);
        super.delete(dbObject, MongoUtil.getDBCollection(MONGO_USER_COLL));
    }

    @Override
    public List<User> findAllUsers() {

        List<User> userList = new ArrayList<User>();
        List<DBObject> testList = super.findAll(MongoUtil.getDBCollection(MONGO_USER_COLL));
        for (DBObject dbObject : testList) {
            User user = populateUser(dbObject);
            userList.add(user);
        }

        return userList;
    }



    /***********************************************************************
     * Mongo convenience methods                                           *
     ***********************************************************************/

    /**
     * populates a User object based on the fields of a DBObject
     *
     * @param dbObject - object with fields we want mapped to a User
     * @return - populated user object
     */
    private User populateUser(DBObject dbObject) {

        User user = new User();

        user.id = dbObject.get("_id").toString();
        user.username = dbObject.get("username").toString();
//        user.password = dbObject.get("password").toString();
        // TODO: only throwing NPE on this one.....why?
//        user.temporaryPassword = dbObject.get("temporaryPassword").toString();
//        user.temporaryPasswordExpiration = (Date) dbObject.get("temporaryPasswordExpiration");
//        user.lastLogin = (Date) dbObject.get("lastLogin");
//        user.failedLoginAttempts = (Integer) dbObject.get("failedLoginAttempts");
//        user.roles = (ArrayList) dbObject.get("roles");
        user.created = (Date) dbObject.get("created");
        user.updated = (Date) dbObject.get("updated");

        return user;
    }

    /**
     * Builds a database object based on the fields the passed in User has
     *
     * @param user - user to map the fields from
     * @return - populated dbobject
     */
    public BasicDBObject createDBObjectFromUser(User user) {

        BasicDBObject dbObject = new BasicDBObject();

        if (user.id != null) {
            dbObject.append("_id", new ObjectId(user.id));
        }
        dbObject.append("username", user.username);
        dbObject.append("password", user.password);
        dbObject.append("temporaryPassword", user.temporaryPassword);
        dbObject.append("temporaryPasswordExpiration", user.temporaryPasswordExpiration);
        dbObject.append("salt", user.salt);
        dbObject.append("lastLogin", user.lastLogin);
        dbObject.append("failedLoginAttempts", user.failedLoginAttempts);
        dbObject.append("roles", user.roles);
        dbObject.append("created", user.created);
        dbObject.append("updated", user.updated);

        return dbObject;
    }
}
