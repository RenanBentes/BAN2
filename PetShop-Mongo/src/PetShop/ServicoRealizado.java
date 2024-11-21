package PetShop;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Scanner;

import main.Conexao;

public class ServicoRealizado {
    private int idServico;
    private String data; // Representação da data como String
    private String status;
    private int idDescricaoServico;
    private int idPet;

    // Construtores
    public ServicoRealizado() {}

    public ServicoRealizado(int idServico, String data, String status, int idDescricaoServico, int idPet) {
        this.idServico = idServico;
        this.data = data;
        this.status = status;
        this.idDescricaoServico = idDescricaoServico;
        this.idPet = idPet;
    }

    // Getters e Setters
    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdDescricaoServico() {
        return idDescricaoServico;
    }

    public void setIdDescricaoServico(int idDescricaoServico) {
        this.idDescricaoServico = idDescricaoServico;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public static void adicionarServico() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do serviço: ");
        int idServico = scanner.nextInt();

        System.out.println("Digite a data do serviço (formato dd/MM/yyyy): ");
        scanner.nextLine(); // Consumir quebra de linha
        String data = scanner.nextLine();

        System.out.println("Digite o status do serviço: ");
        String status = scanner.nextLine();

        System.out.println("Digite o ID da descrição do serviço: ");
        int idDescricaoServico = scanner.nextInt();

        System.out.println("Digite o ID do pet: ");
        int idPet = scanner.nextInt();

        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

        Document document = new Document("idServico", idServico)
                .append("data", data)
                .append("status", status)
                .append("idDescricaoServico", idDescricaoServico)
                .append("idPet", idPet);

        try {
            collection.insertOne(document);
            System.out.println("Serviço adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar serviço: " + e.getMessage());
        }
    }

    public static void listarServicos() {
        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

        System.out.println("Listando todos os serviços:");
        for (Document doc : collection.find()) {
            int idServico = doc.getInteger("idServico");
            String data = doc.getString("data");
            String status = doc.getString("status");
            int idDescricaoServico = doc.getInteger("idDescricaoServico");
            int idPet = doc.getInteger("idPet");

            System.out.println("ID Serviço: " + idServico +
                    ", Data: " + data +
                    ", Status: " + status +
                    ", ID Descrição Serviço: " + idDescricaoServico +
                    ", ID Pet: " + idPet);
        }
    }

    public static ServicoRealizado buscarPorId(int idServico) {
        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

        Document doc = collection.find(Filters.eq("idServico", idServico)).first();
        if (doc != null) {
            return new ServicoRealizado(
                    doc.getInteger("idServico"),
                    doc.getString("data"),
                    doc.getString("status"),
                    doc.getInteger("idDescricaoServico"),
                    doc.getInteger("idPet")
            );
        }
        return null;
    }

    public static void atualizarServico() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do serviço a ser atualizado: ");
        int idServico = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        ServicoRealizado servico = buscarPorId(idServico);
        if (servico != null) {
            System.out.println("Digite a nova data (deixe em branco para manter a atual): ");
            String novaData = scanner.nextLine();
            if (!novaData.isEmpty()) {
                servico.setData(novaData);
            }

            System.out.println("Digite o novo status (deixe em branco para manter o atual): ");
            String novoStatus = scanner.nextLine();
            if (!novoStatus.isEmpty()) {
                servico.setStatus(novoStatus);
            }

            System.out.println("Digite o novo ID da descrição do serviço (deixe em branco para manter o atual): ");
            String novoIdDescricaoStr = scanner.nextLine();
            if (!novoIdDescricaoStr.isEmpty()) {
                servico.setIdDescricaoServico(Integer.parseInt(novoIdDescricaoStr));
            }

            System.out.println("Digite o novo ID do pet (deixe em branco para manter o atual): ");
            String novoIdPetStr = scanner.nextLine();
            if (!novoIdPetStr.isEmpty()) {
                servico.setIdPet(Integer.parseInt(novoIdPetStr));
            }

            MongoDatabase database = Conexao.getDatabase();
            MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

            try {
                collection.updateOne(Filters.eq("idServico", idServico), new Document("$set",
                        new Document("data", servico.getData())
                                .append("status", servico.getStatus())
                                .append("idDescricaoServico", servico.getIdDescricaoServico())
                                .append("idPet", servico.getIdPet())
                ));
                System.out.println("Serviço atualizado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao atualizar serviço: " + e.getMessage());
            }
        } else {
            System.out.println("Serviço não encontrado.");
        }
    }

    public static void removerServico() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do serviço a ser removido: ");
        int idServico = scanner.nextInt();

        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

        try {
            collection.deleteOne(Filters.eq("idServico", idServico));
            System.out.println("Serviço removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao remover serviço: " + e.getMessage());
        }
    }
}
