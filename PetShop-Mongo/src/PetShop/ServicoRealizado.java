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

    // Construtor
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
        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> servicoCollection = database.getCollection("ServicoRealizado");
        MongoCollection<Document> petCollection = database.getCollection("Pet");
        MongoCollection<Document> descricaoCollection = database.getCollection("DescricaoServico");

        // Solicitar e validar o ID da descrição do serviço
        int idDescricaoServico;
        while (true) {
            System.out.println("Digite o ID da descrição do serviço: ");
            idDescricaoServico = scanner.nextInt();
            Document descricaoDoc = descricaoCollection.find(new Document("idDescricaoServico", idDescricaoServico)).first();
            if (descricaoDoc != null) {
                break; // ID válido, prosseguir
            } else {
                System.out.println("Erro: Descrição de serviço com ID " + idDescricaoServico + " não encontrada. Tente novamente.");
            }
        }

        // Solicitar e validar o ID do pet
        int idPet;
        while (true) {
            System.out.println("Digite o ID do pet: ");
            idPet = scanner.nextInt();
            Document petDoc = petCollection.find(new Document("idPet", idPet)).first();
            if (petDoc != null) {
                break; // ID válido, prosseguir
            } else {
                System.out.println("Erro: Pet com ID " + idPet + " não encontrado. Tente novamente.");
            }
        }

        scanner.nextLine(); // Consumir quebra de linha

        // Solicitar os outros campos
        System.out.println("Digite a data do serviço (formato dd/mm/yyyy): ");
        String data = scanner.nextLine();

        // Validar o status do serviço
        String status;
        while (true) {
            System.out.println("Digite o status do serviço (Agendado, Concluído, Cancelado): ");
            status = scanner.nextLine();
            if (status.equalsIgnoreCase("Agendado") ||
                    status.equalsIgnoreCase("Concluído") ||
                    status.equalsIgnoreCase("Cancelado")) {
                break; // Status válido, prosseguir
            } else {
                System.out.println("Erro: Status inválido. Por favor, insira um dos seguintes: Agendado, Concluído, Cancelado.");
            }
        }

        // Gerar um novo ID incremental para o serviço
        Document ultimoServico = servicoCollection.find()
                .sort(new Document("idServico", -1))
                .first();
        int novoIdServico = (ultimoServico != null) ? ultimoServico.getInteger("idServico", 0) + 1 : 1;

        // Criar documento do novo serviço
        Document servicoDoc = new Document("idServico", novoIdServico)
                .append("data", data)
                .append("status", status)
                .append("idDescricaoServico", idDescricaoServico)
                .append("idPet", idPet);

        try {
            servicoCollection.insertOne(servicoDoc);
            System.out.println("Serviço adicionado com sucesso! ID do serviço: " + novoIdServico);
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

            // Solicitar e validar o novo status
            String novoStatus;
            while (true) {
                System.out.println("Digite o novo status (Agendado, Concluído, Cancelado). Deixe em branco para manter o atual: ");
                novoStatus = scanner.nextLine();
                if (novoStatus.isEmpty() ||
                        novoStatus.equalsIgnoreCase("Agendado") ||
                        novoStatus.equalsIgnoreCase("Concluído") ||
                        novoStatus.equalsIgnoreCase("Cancelado")) {
                    break; // Status válido, prosseguir
                } else {
                    System.out.println("Erro: Status inválido. Por favor, insira um dos seguintes: Agendado, Concluído, Cancelado.");
                }
            }
            if (!novoStatus.isEmpty()) {
                servico.setStatus(novoStatus);
            }

            System.out.println("Digite o novo ID da descrição do serviço (deixe em branco para manter o atual): ");
            String novoIdDescricaoStr = scanner.nextLine();
            if (!novoIdDescricaoStr.isEmpty()) {
                int novoIdDescricao = Integer.parseInt(novoIdDescricaoStr);

                // Validar se o ID da descrição do serviço existe
                MongoDatabase database = Conexao.getDatabase();
                MongoCollection<Document> descricaoCollection = database.getCollection("DescricaoServico");
                Document descricaoDoc = descricaoCollection.find(new Document("idDescricaoServico", novoIdDescricao)).first();
                if (descricaoDoc != null) {
                    servico.setIdDescricaoServico(novoIdDescricao);
                } else {
                    System.out.println("Erro: Descrição de serviço com ID " + novoIdDescricao + " não encontrada. Atualização cancelada.");
                    return;
                }
            }

            System.out.println("Digite o novo ID do pet (deixe em branco para manter o atual): ");
            String novoIdPetStr = scanner.nextLine();
            if (!novoIdPetStr.isEmpty()) {
                int novoIdPet = Integer.parseInt(novoIdPetStr);

                // Validar se o ID do pet existe
                MongoDatabase database = Conexao.getDatabase();
                MongoCollection<Document> petsCollection = database.getCollection("Pet");
                Document petDoc = petsCollection.find(new Document("idPet", novoIdPet)).first();
                if (petDoc != null) {
                    servico.setIdPet(novoIdPet);
                } else {
                    System.out.println("Erro: Pet com ID " + novoIdPet + " não encontrado. Atualização cancelada.");
                    return;
                }
            }

            // Atualizar os dados no banco
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