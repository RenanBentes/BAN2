package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import main.Conexao;
import java.util.ArrayList;
import java.util.Scanner;

public class Raca {
    private int idPetRaca;
    private String descricao;

    // Construtor
    public Raca() {}

    // Getters e Setters
    public int getIdPetRaca() {
        return idPetRaca;
    }

    public void setIdPetRaca(int idPetRaca) {
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
        String descricao = scanner.nextLine().trim();

        if (descricao.isEmpty()) {
            System.out.println("Erro: A descrição não pode estar vazia.");
            return;
        }

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");

            // Geração automática de ID
            Document ultimaRaca = racasCollection.find().sort(new Document("idPetRaca", -1)).first();
            int novoId = (ultimaRaca != null) ? ultimaRaca.getInteger("idPetRaca") + 1 : 1;

            Document racaDoc = new Document("idPetRaca", novoId)
                    .append("descricao", descricao);

            racasCollection.insertOne(racaDoc);
            System.out.println("Raça cadastrada com sucesso! ID: " + novoId);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar raça: " + e.getMessage());
        }
    }

    // Lista todas as raças
    public static void listarRacas() {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");

            System.out.println("Lista de Raças:");
            for (Document doc : racasCollection.find()) {
                int id = doc.getInteger("idPetRaca");
                String descricao = doc.getString("descricao");
                System.out.println("ID: " + id + ", Descrição: " + descricao);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar raças: " + e.getMessage());
        }
    }

    // Atualiza uma raça pelo ID
    public static void atualizarRaca() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID da raça que deseja atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");

            Document racaDoc = racasCollection.find(new Document("idPetRaca", id)).first();
            if (racaDoc == null) {
                System.out.println("Erro: Raça com ID " + id + " não encontrada.");
                return;
            }

            System.out.println("Digite a nova descrição da raça: ");
            String novaDescricao = scanner.nextLine().trim();

            if (novaDescricao.isEmpty()) {
                System.out.println("Erro: A descrição não pode estar vazia.");
                return;
            }

            Document update = new Document("$set", new Document("descricao", novaDescricao));
            racasCollection.updateOne(new Document("idPetRaca", id), update);
            System.out.println("Raça atualizada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar raça: " + e.getMessage());
        }
    }

    // Remove uma raça pelo ID
    public static void removerRaca() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID da raça que deseja remover: ");
        int id = scanner.nextInt();

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> racasCollection = mongoClient.getDatabase("PetShop").getCollection("Raca");

            long deletedCount = racasCollection.deleteOne(new Document("idPetRaca", id)).getDeletedCount();
            if (deletedCount > 0) {
                System.out.println("Raça removida com sucesso!");
            } else {
                System.out.println("Erro: Raça com ID " + id + " não encontrada.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao remover raça: " + e.getMessage());
        }
    }
}
