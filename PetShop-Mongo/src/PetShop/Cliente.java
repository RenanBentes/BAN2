package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Scanner;
import main.Conexao;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente() {}

    // Getters e Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static boolean existeCliente(String cpf) {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> clientes = mongoClient.getDatabase("PetShop").getCollection("Cliente");
            Document query = new Document("cpf", cpf);
            long count = clientes.countDocuments(query);
            return count > 0;
        }
    }

    public static void adicionarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.println("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();
        System.out.println("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        System.out.println("Digite o email do cliente: ");
        String email = scanner.nextLine();

        if (existeCliente(cpf)) {
            System.out.println("Erro: Cliente com CPF " + cpf + " já existe.");
            return;
        }

        Document cliente = new Document("nome", nome)
                .append("cpf", cpf)
                .append("telefone", telefone)
                .append("email", email);


    }

    public static void listarClientes() {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> clientes = mongoClient.getDatabase("PetShop").getCollection("Cliente");
            MongoCursor<Document> cursor = clientes.find().iterator();

            System.out.println("Lista de Clientes:");
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
            }
        }
    }

    public static void atualizarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do cliente a ser atualizado: ");
        String id = scanner.nextLine();

        Cliente cliente = buscarPorId(id);
        if (cliente != null) {
            System.out.println("Atualizando informações do cliente: " + cliente.getNome());
            System.out.println("Novo nome (deixe em branco para manter o atual): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) {
                cliente.setNome(nome);
            }
            System.out.println("Novo CPF (deixe em branco para manter o atual): ");
            String cpf = scanner.nextLine();
            if (!cpf.isEmpty()) {
                if (existeCliente(cpf)) {
                    System.out.println("Erro: Cliente com CPF " + cpf + " já existe.");
                    return;
                }
                cliente.setCpf(cpf);
            }
            System.out.println("Novo telefone (deixe em branco para manter o atual): ");
            String telefone = scanner.nextLine();
            if (!telefone.isEmpty()) {
                cliente.setTelefone(telefone);
            }
            System.out.println("Novo email (deixe em branco para manter o atual): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                cliente.setEmail(email);
            }

            try (MongoClient mongoClient = Conexao.getConexao()) {
                MongoCollection<Document> clientes = mongoClient.getDatabase("PetShop").getCollection("Cliente");
                Document query = new Document("_id", new ObjectId(id));
                Document update = new Document("$set", new Document("nome", cliente.getNome())
                        .append("cpf", cliente.getCpf())
                        .append("telefone", cliente.getTelefone())
                        .append("email", cliente.getEmail()));
                clientes.updateOne(query, update);
                System.out.println("Cliente atualizado com sucesso!");
            }
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
        }
    }

    public static void removerCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do cliente a ser removido: ");
        String id = scanner.nextLine();

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> clientes = mongoClient.getDatabase("PetShop").getCollection("Cliente");
            Document query = new Document("_id", new ObjectId(id));
            clientes.deleteOne(query);
            System.out.println("Cliente removido com sucesso!");
        }
    }

    public static Cliente buscarPorId(String idCliente) {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> clientes = mongoClient.getDatabase("PetShop").getCollection("Cliente");
            Document query = new Document("_id", new ObjectId(idCliente));
            Document doc = clientes.find(query).first();

            if (doc != null) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(doc.getInteger("idCliente"));
                cliente.setNome(doc.getString("nome"));
                cliente.setCpf(doc.getString("cpf"));
                cliente.setTelefone(doc.getString("telefone"));
                cliente.setEmail(doc.getString("email"));
                return cliente;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente='" + idCliente + '\'' +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
