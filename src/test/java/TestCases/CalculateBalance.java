package TestCases;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;

import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.*;

public class CalculateBalance {

    public static void main(String[] args) throws Exception{
        try {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        DB db = mongoClient.getDB("admin");
        System.out.println("Connected Succesfully");
        MongoDatabase mongoDatabase =mongoClient.getDatabase("admin");
        MongoCollection collection = mongoDatabase.getCollection("test");


            BsonArray operands = new BsonArray();
            BsonDocument subtract = new BsonDocument("$subtract", operands);


            AggregateIterable<BsonDocument> rslt = collection.aggregate(Arrays.asList(
                    project(fields(
                            include(
                                    "totalAmount",
                                    "onHoldAmount"
                            ),
                            new BsonDocument("remaining", subtract)
                            )
                    )
            ));

            System.out.println(rslt);




        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
