package main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {
    private static final String URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "PetShop";
    private static MongoClient mongoClient;

    // Obtém o cliente MongoDB, criando-o se necessário
    public static MongoClient getMongoClient() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.WARNING); // Exibir apenas erros
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URI);
        }
        return mongoClient;
    }

    // Retorna a instância do banco de dados, garantindo que a conexão esteja ativa
    public static MongoDatabase getDatabase() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.WARNING); // Exibir apenas erros
        MongoClient client = getMongoClient();

        if (client != null) {
            return client.getDatabase(DATABASE_NAME);
        } else {
            throw new IllegalStateException("Não foi possível conectar ao banco de dados.");
        }
    }

    // Fecha a conexão com o MongoDB, garantindo que o cliente seja nulo após o fechamento
    public static void fecharConexao() {
        if (mongoClient != null) {
            try {
                mongoClient.close();
                mongoClient = null;
            } catch (Exception e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}