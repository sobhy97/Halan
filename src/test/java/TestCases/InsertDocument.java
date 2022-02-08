package TestCases;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import javax.print.Doc;

public class InsertDocument {

    public static void main(String[] args) throws Exception{

       try {
           MongoClient mongoClient = new MongoClient("localhost",27017);
           DB db = mongoClient.getDB("admin");
           System.out.println("Connected Succesfully");
           MongoDatabase mongoDatabase =mongoClient.getDatabase("admin");
           MongoCollection collection = mongoDatabase.getCollection("test");
           Document document = new Document();
           document.append("accountId","gdx540");
           document.append("totalAmount","5000");
           document.append("onHoldAmount","1000");

           collection.insertOne(document);


       }catch (Exception e)
       {
           System.out.println(e);
       }

    }
}
