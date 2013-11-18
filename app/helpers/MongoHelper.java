package helpers;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import play.Play;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/15/13
 */
public class MongoHelper {

    // pulls data from application.conf and sets local variables
    private static final String DBNAME = Play.application().configuration().getString("mongo.dbName");
    private static final String MONGOURI = Play.application().configuration().getString("mongo.uri");

    /**
     * Configure mongo client by setting db name and table name
     *
     * @return - configured dbcollection
     */
    private static MongoClient getMongoClient() {

        MongoClientURI mongoClientURI = new MongoClientURI(MONGOURI);

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(mongoClientURI);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return mongoClient;
    }

    private static DB getDB() {

        return getMongoClient().getDB(DBNAME);
    }

    public static DBCollection getDBCollection(String collectionName) {

        return getDB().getCollection(collectionName);
    }
}
