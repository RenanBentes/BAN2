package petshop;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import main.Conexao;

public class ServicoRealizado {
    private int idServico;
    private Date data;
    private int idDescricaoServico;
    private int idCliente;
    private int idPet;
    private String status;

    // Construtores
    public ServicoRealizado() {}

    public ServicoRealizado(Date data, int idDescricaoServico, int idCliente, int idPet, String status) {
        this.data = data;
        this.idDescricaoServico = idDescricaoServico;
        this.idCliente = idCliente;
        this.idPet = idPet;
        this.status = status;
    }

    // Getters e Setters
    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdDescricaoServico() {
        return idDescricaoServico;
    }

    public void setIdDescricaoServico(int idDescricaoServico) {
        this.idDescricaoServico = idDescricaoServico;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static void adicionarServico() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Digite a data do serviço (dd/mm/yyyy): ");
        String dataStr = scanner.nextLine();
        Date data = null;
        try {
            data = dateFormat.parse(dataStr);
        } catch (ParseException e) {
            System.out.println("Erro: Data em formato inválido.");
            return;
        }

        System.out.println("Digite o ID da descrição do serviço: ");
        int idDescricaoServico = scanner.nextInt();

        System.out.println("Digite o ID do cliente: ");
        int idCliente = scanner.nextInt();

        System.out.println("Digite o ID do pet: ");
        int idPet = scanner.nextInt();
        scanner.nextLine();  // Consumir a quebra de linha

        System.out.println("Digite o status do serviço: ");
        String status = scanner.nextLine();

        ServicoRealizado servico = new ServicoRealizado(data, idDescricaoServico, idCliente, idPet, status);
        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

        Document document = new Document("data", servico.getData())
                .append("idDescricaoServico", servico.getIdDescricaoServico())
                .append("idCliente", servico.getIdCliente())
                .append("idPet", servico.getIdPet())
                .append("status", servico.getStatus());

        try {
            collection.insertOne(document);
            System.out.println("Serviço realizado cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar serviço: " + e.getMessage());
        }
    }

    public static void listarServicos() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

        for (Document doc : collection.find()) {
            int idServico = doc.getInteger("_id");
            Date data = doc.getDate("data");
            String status = doc.getString("status");
            int idCliente = doc.getInteger("idCliente");
            int idDescricaoServico = doc.getInteger("idDescricaoServico");
            int idPet = doc.getInteger("idPet");

            System.out.println("ID: " + idServico +
                    ", Descrição: " + idDescricaoServico +
                    ", Pet ID: " + idPet +
                    ", Data: " + dateFormat.format(data) +
                    ", Status: " + status +
                    ", Cliente ID: " + idCliente);
        }
    }

    public static void atualizarServico() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Digite o ID do serviço a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        ServicoRealizado servico = buscarPorId(id);
        if (servico != null) {
            System.out.println("Atualizando informações do serviço: ");
            System.out.println("Nova data (deixe em branco para manter a atual, formato dd/mm/yyyy): ");
            String dataStr = scanner.nextLine();
            if (!dataStr.isEmpty()) {
                try {
                    servico.setData(dateFormat.parse(dataStr));
                } catch (ParseException e) {
                    System.out.println("Erro: Data em formato inválido.");
                    return;
                }
            }
            System.out.println("Novo ID de descrição do serviço (deixe em branco para manter o atual): ");
            String idDescricaoStr = scanner.nextLine();
            if (!idDescricaoStr.isEmpty()) {
                servico.setIdDescricaoServico(Integer.parseInt(idDescricaoStr));
            }
            System.out.println("Novo ID do cliente (deixe em branco para manter o atual): ");
            String idClienteStr = scanner.nextLine();
            if (!idClienteStr.isEmpty()) {
                servico.setIdCliente(Integer.parseInt(idClienteStr));
            }
            System.out.println("Novo ID do pet (deixe em branco para manter o atual): ");
            String idPetStr = scanner.nextLine();
            if (!idPetStr.isEmpty()) {
                servico.setIdPet(Integer.parseInt(idPetStr));
            }
            System.out.println("Novo status (deixe em branco para manter o atual): ");
            String status = scanner.nextLine();
            if (!status.isEmpty()) {
                servico.setStatus(status);
            }

            MongoDatabase database = Conexao.getDatabase();
            MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

            Document updatedDocument = new Document("data", servico.getData())
                    .append("idDescricaoServico", servico.getIdDescricaoServico())
                    .append("idCliente", servico.getIdCliente())
                    .append("idPet", servico.getIdPet())
                    .append("status", servico.getStatus());

            try {
                collection.updateOne(new Document("_id", id), new Document("$set", updatedDocument));
                System.out.println("Serviço atualizado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao atualizar serviço: " + e.getMessage());
            }
        } else {
            System.out.println("Serviço com ID " + id + " não encontrado.");
        }
    }

    public static void removerServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do serviço a ser removido: ");
        int id = scanner.nextInt();

        ServicoRealizado servico = buscarPorId(id);
        if (servico != null) {
            MongoDatabase database = Conexao.getDatabase();
            MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

            try {
                collection.deleteOne(new Document("_id", id));
                System.out.println("Serviço removido com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao remover serviço: " + e.getMessage());
            }
        } else {
            System.out.println("Serviço com ID " + id + " não encontrado.");
        }
    }

    public static ServicoRealizado buscarPorId(int idServico) {
        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("ServicoRealizado");

        Document doc = collection.find(new Document("_id", idServico)).first();
        if (doc != null) {
            ServicoRealizado servico = new ServicoRealizado();
            servico.setIdServico(doc.getInteger("_id"));
            servico.setData(doc.getDate("data"));
            servico.setIdDescricaoServico(doc.getInteger("idDescricaoServico"));
            servico.setIdCliente(doc.getInteger("idCliente"));
            servico.setIdPet(doc.getInteger("idPet"));
            servico.setStatus(doc.getString("status"));
            return servico;
        }
        return null;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "ID Serviço: " + idServico + "\n" +
                "Data: " + dateFormat.format(data) + "\n" +
                "Descrição do Serviço: " + idDescricaoServico + "\n" +
                "Cliente ID: " + idCliente + "\n" +
                "Pet ID: " + idPet + "\n" +
                "Status: " + status;
    }
}
