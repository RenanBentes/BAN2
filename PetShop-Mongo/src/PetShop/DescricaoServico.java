package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import main.Conexao;
import java.util.Scanner;

public class DescricaoServico {
    private int idDescricaoServico; // ID como inteiro
    private String servicoDescricao;
    private double valor;
    private int idPetRaca; // Adicionado idPetRaca

    // Construtores
    public DescricaoServico(int idDescricaoServico, String servicoDescricao, double valor, int idPetRaca) {
        this.idDescricaoServico = idDescricaoServico;
        this.servicoDescricao = servicoDescricao;
        this.valor = valor;
        this.idPetRaca = idPetRaca;
    }

    // Getters e Setters
    public int getIdDescricaoServico() {
        return idDescricaoServico;
    }

    public void setIdDescricaoServico(int idDescricaoServico) {
        this.idDescricaoServico = idDescricaoServico;
    }

    public String getServicoDescricao() {
        return servicoDescricao;
    }

    public void setServicoDescricao(String servicoDescricao) {
        this.servicoDescricao = servicoDescricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdPetRaca() {
        return idPetRaca;
    }

    public void setIdPetRaca(int idPetRaca) {
        this.idPetRaca = idPetRaca;
    }

    @Override
    public String toString() {
        return "ID: " + idDescricaoServico + ", Descrição: " + servicoDescricao + ", Valor: R$ " + valor + ", ID da Raça: " + idPetRaca;
    }

    public static void adicionarDescricaoServico() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID da descrição do serviço: ");
        int idDescricaoServico = scanner.nextInt();
        scanner.nextLine(); // Consumir o \n após o nextInt()

        System.out.println("Digite a descrição do serviço: ");
        String descricao = scanner.nextLine();

        System.out.println("Digite o valor do serviço: ");
        double valor = scanner.nextDouble();

        System.out.println("Digite o ID da raça: ");
        int idPetRaca = scanner.nextInt();

        DescricaoServico descricaoServico = new DescricaoServico(idDescricaoServico, descricao, valor, idPetRaca);

        Document descricaoDoc = new Document("idDescricaoServico", descricaoServico.getIdDescricaoServico())
                .append("servicoDescricao", descricaoServico.getServicoDescricao())
                .append("valor", descricaoServico.getValor())
                .append("idPetRaca", descricaoServico.getIdPetRaca());

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");
            descricaoServicoCollection.insertOne(descricaoDoc);
            System.out.println("Descrição de serviço adicionada com sucesso!");
        }
    }

    public static void listarDescricoesServico() {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");

            System.out.println("Lista de Descrições de Serviço:");
            for (Document doc : descricaoServicoCollection.find()) {
                int idDescricaoServico = doc.getInteger("idDescricaoServico", 0);
                String servicoDescricao = doc.getString("servicoDescricao");
                double valor = doc.getDouble("valor") != null ? doc.getDouble("valor") : 0.0;
                int idPetRaca = doc.getInteger("idPetRaca", 0);

                DescricaoServico descricaoServico = new DescricaoServico(idDescricaoServico, servicoDescricao, valor, idPetRaca);
                System.out.println(descricaoServico);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar descrições de serviço: " + e.getMessage());
        }
    }

    public static void atualizarDescricaoServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da descrição de serviço a ser atualizada: ");
        int idDescricaoServico = scanner.nextInt();
        scanner.nextLine(); // Consumir o \n após o nextInt()

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");

            Document query = new Document("idDescricaoServico", idDescricaoServico);
            Document doc = descricaoServicoCollection.find(query).first();

            if (doc != null) {
                System.out.println("Digite a nova descrição (deixe em branco para manter): ");
                String descricao = scanner.nextLine();
                if (descricao.isEmpty()) {
                    descricao = doc.getString("servicoDescricao");
                }

                System.out.println("Digite o novo valor (deixe em branco para manter): ");
                String valorStr = scanner.nextLine();
                double valor = valorStr.isEmpty() ? doc.getDouble("valor") : Double.parseDouble(valorStr);

                System.out.println("Digite o novo ID da raça (deixe em branco para manter): ");
                String idPetRacaStr = scanner.nextLine();
                int idPetRaca = idPetRacaStr.isEmpty() ? doc.getInteger("idPetRaca", 0) : Integer.parseInt(idPetRacaStr);

                Document update = new Document("$set", new Document("servicoDescricao", descricao)
                        .append("valor", valor)
                        .append("idPetRaca", idPetRaca));
                descricaoServicoCollection.updateOne(query, update);
                System.out.println("Descrição de serviço atualizada com sucesso!");
            } else {
                System.out.println("Descrição de serviço com ID " + idDescricaoServico + " não encontrada.");
            }
        }
    }

    public static void removerDescricaoServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da descrição de serviço a ser removida: ");
        int idDescricaoServico = scanner.nextInt();

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");

            Document query = new Document("idDescricaoServico", idDescricaoServico);
            descricaoServicoCollection.deleteOne(query);
            System.out.println("Descrição de serviço removida com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao remover descrição de serviço: " + e.getMessage());
        }
    }
}
