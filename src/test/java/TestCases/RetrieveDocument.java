package TestCases;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class RetrieveDocument {

    public static void main(String[] args) throws Exception{
        try {
            MongoClient mongoClient = new MongoClient("localhost",27017);
            DB db = mongoClient.getDB("admin");
            System.out.println("Connected Succesfully");
            MongoDatabase mongoDatabase =mongoClient.getDatabase("admin");
            MongoCollection collection = mongoDatabase.getCollection("test");


            FindIterable<Document> onlyTest= collection.find(new Document("accountId","gdx540"));
            onlyTest.forEach((Block<? super Document>) d-> System.out.println(d.toJson()
            ));


        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
