package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import main.Conexao;
import java.util.ArrayList;
import java.util.Scanner;

public class Raca {
    private String idPetRaca; // Alterado para String, pois MongoDB usa ObjectId
    private String descricao;

    // Construtores
    public Raca() {}

    public Raca(String descricao) {
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getIdPetRaca() {
        return idPetRaca;
    }

    public void setIdPetRaca(String idPetRaca) {
        this.idPetRaca = idPetRaca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Adiciona uma nova raça
    public static void adicionarRaca() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a descrição da raça: ");
        String descricao = scanner.nextLine();

        Raca raca = new Raca(descricao);

        Document racaDoc = new Document("descricao", raca.getDescricao());

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");
            racasCollection.insertOne(racaDoc);
            System.out.println("Raça cadastrada com sucesso!");
        }
    }

    // Lista todas as raças
    public static void listarRacas() {
        ArrayList<Raca> racas = new ArrayList<>();

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");
            MongoCursor<Document> cursor = racasCollection.find().iterator();

            System.out.println("Lista de Raças:");
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Raca raca = new Raca();
                raca.setIdPetRaca(doc.getObjectId("_id").toHexString());
                raca.setDescricao(doc.getString("descricao"));
                System.out.println("ID: " + raca.getIdPetRaca() + ", Descrição: " + raca.getDescricao());
                racas.add(raca);
            }
        }
    }

    // Atualiza informações de uma raça
    public static void atualizarRaca() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID da raça que deseja atualizar: ");
        String id = scanner.nextLine();

        Raca raca = buscarPorId(id);
        if (raca != null) {
            System.out.println("Digite a nova descrição da raça: ");
            String novaDescricao = scanner.nextLine();
            raca.setDescricao(novaDescricao);

            try (MongoClient mongoClient = Conexao.getConexao()) {
                MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");
                Document query = new Document("_id", new ObjectId(id));
                Document update = new Document("$set", new Document("descricao", raca.getDescricao()));
                racasCollection.updateOne(query, update);
                System.out.println("Raça atualizada com sucesso!");
            }
        } else {
            System.out.println("Raça com ID " + id + " não encontrada.");
        }
    }

    // Remove uma raça pelo ID
    public static void removerRaca() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da raça que deseja remover: ");
        String id = scanner.nextLine();

        Raca raca = buscarPorId(id);
        if (raca != null) {
            try (MongoClient mongoClient = Conexao.getConexao()) {
                MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");
                Document query = new Document("_id", new ObjectId(id));
                racasCollection.deleteOne(query);
                System.out.println("Raça removida com sucesso!");
            }
        } else {
            System.out.println("Raça com ID " + id + " não encontrada.");
        }
    }

    // Busca uma raça pelo ID
    public static Raca buscarPorId(String idPetRaca) {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");
            Document query = new Document("_id", new ObjectId(idPetRaca));
            Document doc = racasCollection.find(query).first();

            if (doc != null) {
                Raca raca = new Raca();
                raca.setIdPetRaca(doc.getObjectId("_id").toHexString());
                raca.setDescricao(doc.getString("descricao"));
                return raca;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Raca{" +
                "idPetRaca='" + idPetRaca + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
