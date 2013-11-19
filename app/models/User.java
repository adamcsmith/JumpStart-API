package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import helpers.MongoHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import play.Play;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.libs.Crypto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static play.data.validation.Constraints.Required;

/**
 * Created with IntelliJ IDEA for jumpstart
 * User: phutchinson
 * Date: 3/16/13
 * Time: 4:38 PM
 */
@Entity
@Table(name = "APP_USER")
public class User extends ModelBase {

    // pulls data from application.conf and sets local variables
    private static final String DB_TYPE = Play.application().configuration().getString("jumpstart.dbtype");
    private static final String MONGO = "mongo";
    private static final String MYSQL = "mysql";

    private static final String MONGO_USER_COLL = Play.application().configuration().getString("mongo.userCollectionName");

    /***********************************************************************
     * Enums and Static fields                                             *
     ***********************************************************************/

    public enum RoleValue {
        STANDARD, ADMIN
    }


    /***********************************************************************
     * Class Member Variables                                              *
     ***********************************************************************/

    /**
     * The username that the user will use to log in to the system
     */
    @Required
    public String username;

    /**
     * encrypted password value
     */
    @Required
    public String password;

    @Transient
    @Constraints.MinLength(6)
    @Constraints.MaxLength(12)
    public String plainTextPassword;

    /**
     * encrypted password value the can be compared against the password to check that the user entered the same value
     * for both.  Not persisted
     */
    @Transient
    @Required
    public String confirmPassword;

    /**
     * A temporary password generated by the system as part of the reset password functionality.  Use this instead
     * of modifying the password directly so that people can't randomly reset other people's passwords.  A user can
     * be authenticated with the password or tempPassword if the tempPassword is used before the passwordExpiration date
     */
    public String temporaryPassword;

    /**
     * Date when the User's temporary password is no longer valid.
     */
    public Date temporaryPasswordExpiration;

    /**
     * A unique value that is used in the generation of passwords
     */
    public String salt = BCrypt.gensalt();

    /**
     * Timestamp of when the user last logged in to the application
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date lastLogin;

    /**
     * The number of times that someone has tried to login with this username with an incorrect password.  Should be
     * reset to zero upon successful login
     */
    public int failedLoginAttempts;

    /**
     * A list of roles that the User has
     */
    @ElementCollection
    @Enumerated(EnumType.STRING)
    public List<RoleValue> roles = new ArrayList<RoleValue>();


    /***********************************************************************
     * Persistence Operations                                              *
     ***********************************************************************/

    public static Finder<Long,User> find = new Finder<Long,User>(
            Long.class, User.class
    );


    /***********************************************************************
     * Login Handlers                                                      *
     ***********************************************************************/

    public static User connect(String username, String password){

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new RuntimeException("error.usernameAndPasswordRequired");
        }

        User user = findUserByUsername(username);

        //no user found
        if(user == null){
            throw new RuntimeException("error.userNotFound");
        }

        //password does not match
        if(!user.password.equals(user.hashPassword(password))) {
            user.failedLoginAttempts++;
            user.save();
            throw new RuntimeException("error.badPassword");
        }

        return user;
    }


    /***********************************************************************
     * Methods for handling password values                                *
     ***********************************************************************/

    /**
     * Sets the user's password to a hash of the plain text password argument and clears the password expiration.
     *
     * @param password Plain text password value.
     */
    public void setPassword(final String password) {
        this.plainTextPassword = password;
        this.password = hashPassword(password);
        //setting a new actual password clears out any temporary password data
        this.temporaryPassword = null;
        this.temporaryPasswordExpiration = null;
    }


    /**
     * Sets the user's password confirmation string to a hash of the plain text value.  Note that confirmPassword is a
     * transient value and will not be stored in the database
     * @param password
     */
    public void setConfirmPassword(final String password){
        this.confirmPassword = hashPassword(password);
    }

    /**
     * Sets a temporary password which is set to expire on the current date.
     *
     * @param password plain text password value.
     */
    public void setTemporaryPassword(final String password) {
        this.temporaryPassword = hashPassword(password);
        this.temporaryPasswordExpiration = DateUtils.addDays(new Date(), 1);
    }

    /**
     * Determines if the User has an expired temporary password
     * @return
     */
    public boolean temporaryPasswordExpired () {
        return temporaryPasswordExpiration != null && temporaryPasswordExpiration.before(new Date());
    }

    /**
     * Hash a password using the OpenBSD bcrypt scheme.  This can be used to check if a plain-text password matches the
     * encrypted and stored password value for this user.
     * @param password The plain-text password to be hashed
     * @return a String that is the hashed value of the password.
     */
    private String hashPassword(String password) {
        return Crypto.encryptAES(BCrypt.hashpw(password, salt));
    }


    /***********************************************************************
     * Validation Logic                                                    *
     ***********************************************************************/

    public List<ValidationError> validate(){

        List<ValidationError> errors = new ArrayList<ValidationError>();
        if(StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(confirmPassword) && !password.equals(confirmPassword)){
            errors.add(new ValidationError("password", "error.passwordAndConfirmPasswordNoMatch"));
        }

        List<User> duplicates = User.find.where().ilike("username", this.username).findList();
        if(duplicates.size () > 1 || duplicates.size() == 1 && !duplicates.contains(this)){
            errors.add(new ValidationError("username", "error.usernameNotUnique"));
        }

        //return null instead of empty list.
        //see: http://stackoverflow.com/questions/11388269/playframework-illegalstateexception-after-form-validation
        return errors.size() > 0 ? errors : null;
    }


    /***********************************************************************
     * 'Reflection' methods                                                  *
     ***********************************************************************/

    /**
     * Looks up a user in database by their username
     *
     * @param username
     * @return - found user or null user
     */
    public static User findUserByUsername(String username) {

        if (DB_TYPE.equals(MONGO)) {
            DBObject mongoUser = MongoHelper.getDBCollection(MONGO_USER_COLL).findOne(new BasicDBObject("username", username));
            if (mongoUser != null) {
               return populateUser(mongoUser);
            } else {
                return null;
            }
        } else if (DB_TYPE.equals(MYSQL)) {
            return User.find.where().ieq("username", username).findUnique();
        } else {
            throw new RuntimeException("Can't find the database type.  Check the application.conf file " +
                    "for the db.default.type setting.");
        }
    }

    /**
     * Looks up a user in database by their username
     *
     * @param id
     * @return - found user or null user
     */
    public static User findUserById(String id) {

        if (DB_TYPE.equals(MONGO)) {
            DBObject mongoUser = MongoHelper.getDBCollection(MONGO_USER_COLL).findOne(new BasicDBObject("_id", new ObjectId(id)));
            if (mongoUser != null) {
                return populateUser(mongoUser);
            } else {
                return null;
            }
        } else if (DB_TYPE.equals(MYSQL)) {
            return User.find.byId(Long.parseLong(id));
        } else {
            throw new RuntimeException("Can't find the database type.  Check the application.conf file " +
                    "for the db.default.type setting.");
        }
    }

    /**
     *
     * @param user
     * @return
     */
    public static User createUser(User user) {

        User createdUser;

        if (DB_TYPE.equals(MONGO)) {
           BasicDBObject dbObject = createDBObjectFromUser(user);
           DBObject createdDBObject = (DBObject) create(dbObject, MongoHelper.getDBCollection(MONGO_USER_COLL));
           createdUser = populateUser(createdDBObject);
           return createdUser;
        } else if (DB_TYPE.equals(MYSQL)) {
           createdUser = (User) create(user, null);
           return createdUser;
        } else {
            throw new RuntimeException("Can't find the database type.  Check the application.conf file " +
                    "for the db.default.type setting.");
        }
    }

    /**
     *
     * @param user
     */
    public static User updateUser(User user) {

        User updatedUser;

        if (DB_TYPE.equals(MONGO)) {
            BasicDBObject dbObject = createDBObjectFromUser(user);
            DBObject updatedObject = (DBObject) updateObject(dbObject, MongoHelper.getDBCollection(MONGO_USER_COLL));
            updatedUser = populateUser(updatedObject);
            updatedUser.updated = new Date();
            return updatedUser;
        } else if (DB_TYPE.equals(MYSQL)) {
            updatedUser = (User) updateObject(user, null);
            return updatedUser;
        } else {
            throw new RuntimeException("Can't find the database type.  Check the application.conf file " +
                    "for the db.default.type setting.");
        }
    }

    /**
     *
     * @param user
     */
    public static void deleteUser(User user) {

        if (DB_TYPE.equals(MONGO)) {
            BasicDBObject dbObject = createDBObjectFromUser(user);
            delete(dbObject, MongoHelper.getDBCollection(MONGO_USER_COLL));
        } else if (DB_TYPE.equals(MYSQL)) {
            delete(user, null);
        } else {
            throw new RuntimeException("Can't find the database type.  Check the application.conf file " +
                    "for the db.default.type setting.");
        }

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
    private static User populateUser(DBObject dbObject) {

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
    public static BasicDBObject createDBObjectFromUser(User user) {

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
