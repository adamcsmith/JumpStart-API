package utils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import play.Play;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: adamcsmith
 * Date: 11/20/13
 */
public class MongoUtil {

    // pulls data from application.conf and sets local variables
    private static final String DB_NAME = Play.application().configuration().getString("mongo.dbName");
    private static final String MONGO_URI = Play.application().configuration().getString("mongo.uri");

    /**
     * Configure mongo client by setting db name and table name
     *
     * @return - configured dbcollection
     */
    private static MongoClient getMongoClient() {

        MongoClientURI mongoClientURI = new MongoClientURI(MONGO_URI);

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(mongoClientURI);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return mongoClient;
    }

    private static DB getDB() {

        return getMongoClient().getDB(DB_NAME);
    }

    public static DBCollection getDBCollection(String collectionName) {

        return getDB().getCollection(collectionName);
    }

}
