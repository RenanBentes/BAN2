package main;

import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexao {
    private static final String URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "PetShop";
    private static MongoDatabase database;
    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyConnectionString(new ConnectionString(URI))
                            .build()
            );
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
        return database;
    }

    public static MongoClient getConexao() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(new ConnectionString(URI));
        }
        return mongoClient;
    }

}

