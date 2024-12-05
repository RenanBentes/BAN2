package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import main.Conexao;

import java.util.ArrayList;
import java.util.List;

public class Relatorio {

    public static void totalPetsPorCliente() {
        MongoClient mongoClient = null;
        MongoDatabase database = null;
        try {
            // Conectar com o MongoDB
            mongoClient = Conexao.getMongoClient();
            database = Conexao.getDatabase();

            MongoCollection<Document> petsCollection = database.getCollection("Pet");

            List<Document> resultados = petsCollection.aggregate(List.of(
                    new Document("$group", new Document("_id", "$idCliente")
                            .append("totalPets", new Document("$sum", 1))),
                    new Document("$lookup", new Document("from", "Cliente")
                            .append("localField", "_id")
                            .append("foreignField", "idCliente")
                            .append("as", "clienteInfo")),
                    new Document("$project", new Document("idCliente", "$_id")
                            .append("totalPets", 1)
                            .append("nomeCliente", new Document("$arrayElemAt", List.of("$clienteInfo.nome", 0)))),
                    new Document("$sort", new Document("totalPets", -1)) // Ordenação decrescente
            )).into(new ArrayList<>());

            System.out.println("Relatório: Total de Pets por Cliente");
            for (Document doc : resultados) {
                System.out.printf("Cliente ID: %s, Nome: %s, Total de Pets: %d%n",
                        String.valueOf(doc.get("_id")),  // Converte o ID inteiro para String
                        doc.getString("nomeCliente"),
                        doc.getInteger("totalPets"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao gerar o relatório: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                Conexao.fecharConexao(); // Fechar a conexão
            }
        }
    }

    public static void servicosPorPet() {
        MongoClient mongoClient = null;
        MongoDatabase database = null;
        try {
            // Conectar com o MongoDB
            mongoClient = Conexao.getMongoClient();
            database = Conexao.getDatabase();

            MongoCollection<Document> servicosCollection = database.getCollection("ServicoRealizado");

            // Realizando a agregação
            List<Document> resultados = servicosCollection.aggregate(List.of(
                    new Document("$lookup", new Document("from", "Pet")
                            .append("localField", "idPet")
                            .append("foreignField", "idPet")
                            .append("as", "petInfo")),
                    new Document("$unwind", "$petInfo"), // Desfazendo o array para acessar diretamente os campos de Pet
                    new Document("$project", new Document("idPet", 1)
                            .append("servico", "$descricao")
                            .append("nomePet", "$petInfo.nome")),  // Nome do pet agora diretamente acessado
                    new Document("$group", new Document("_id", "$idPet")
                            .append("totalServicos", new Document("$sum", 1))
                            .append("nomePet", new Document("$first", "$nomePet"))),
                    new Document("$sort", new Document("totalServicos", -1)) // Ordenação decrescente
            )).into(new ArrayList<>());

            // Exibindo os resultados
            System.out.println("Relatório: Serviços Realizados por Pet");
            for (Document doc : resultados) {
                String petId = doc.get("_id") != null ? doc.get("_id").toString() : "Desconhecido"; // Garantir que o ID seja convertido
                System.out.printf("Pet ID: %s, Nome: %s, Total de Serviços: %d%n",
                        petId,
                        doc.getString("nomePet"),
                        doc.getInteger("totalServicos"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao gerar o relatório: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                Conexao.fecharConexao(); // Fechar a conexão
            }
        }
    }

    public static void somaValoresPorServico() {
        try (MongoClient mongoClient = Conexao.getMongoClient()) { // Conexão automática com try-with-resources
            MongoDatabase database = Conexao.getDatabase();
            MongoCollection<Document> servicosCollection = database.getCollection("ServicoRealizado");

            // Realizando a agregação com conversão explícita para Double
            List<Document> resultados = servicosCollection.aggregate(List.of(
                    new Document("$match", new Document("status", "Concluído")), // Filtra serviços concluídos
                    new Document("$project", new Document("valor",
                            new Document("$add", List.of("$valor", 0.0)))), // Converte valor para Double somando 0.0
                    new Document("$group", new Document("_id", "$idDescricaoServico")
                            .append("total", new Document("$sum", "$valor"))), // Soma os valores
                    new Document("$lookup", new Document("from", "DescricaoServico")
                            .append("localField", "_id")
                            .append("foreignField", "idDescricaoServico")
                            .append("as", "descricaoInfo")), // Junta com a tabela de descrições
                    new Document("$project", new Document("descricaoServico",
                            new Document("$arrayElemAt", List.of("$descricaoInfo.servicoDescricao", 0)))
                            .append("total", 1)), // Extrai a descrição e mantém o total
                    new Document("$sort", new Document("total", -1)) // Ordena por total (decrescente)
            )).into(new ArrayList<>());

            // Exibindo os resultados
            System.out.println("Soma dos valores dos serviços 'Concluídos' por descrição:");
            for (Document doc : resultados) {
                String descricaoServico = doc.getString("descricaoServico");
                Double total = doc.getDouble("total");

                if (descricaoServico == null || total == null) {
                    System.out.println("Erro: Documento com dados ausentes.");
                    continue;
                }

                System.out.printf("Serviço: %s, Total: %.2f%n", descricaoServico, total);
            }
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valor para Double: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao calcular a soma dos valores por serviço: " + e.getMessage());
        }
    }


}
