package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Scanner;
import main.Conexao;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    // Construtor
    public Cliente() {}

    // Getters e Setters
<<<<<<< HEAD
    public long getIdCliente() {
=======
    public int getIdCliente() {
>>>>>>> origin/final
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

    // Verifica se um cliente com o CPF fornecido já existe
    public static boolean existeCliente(String cpf) {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> clientes = mongoClient.getDatabase("PetShop").getCollection("Cliente");
            Document query = new Document("cpf", cpf);
            return clientes.countDocuments(query) > 0;
        } catch (Exception e) {
            System.err.println("Erro ao verificar a existência do cliente: " + e.getMessage());
            return false;
        }
    }

    // Adiciona um novo cliente ao banco de dados
    public static void adicionarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do cliente: ");
        String nome = scanner.nextLine().trim();

        System.out.println("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine().trim();

        // Validação de CPF básico
        if (cpf.length() != 11 || !cpf.matches("\\d+")) {
            System.out.println("Erro: CPF inválido. Certifique-se de que o CPF tenha 11 dígitos numéricos.");
            return;
        }

        System.out.println("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine().trim();

        System.out.println("Digite o email do cliente: ");
        String email = scanner.nextLine().trim();

        // Verifica se o cliente já existe
        if (existeCliente(cpf)) {
            System.out.println("Erro: Cliente com CPF " + cpf + " já existe.");
            return;
        }

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> clientes = mongoClient.getDatabase("PetShop").getCollection("Cliente");
            Document ultimoCliente = clientes.find().sort(new Document("idCliente", -1)).first();
            long novoIdCliente = (ultimoCliente != null) ? ultimoCliente.getLong("idCliente") + 1 : 1;

            Document clienteDoc = new Document("idCliente", novoIdCliente)
                    .append("nome", nome)
                    .append("cpf", cpf)
                    .append("telefone", telefone)
                    .append("email", email);

            clientes.insertOne(clienteDoc);
            System.out.println("Cliente adicionado com sucesso! ID: " + novoIdCliente);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar o cliente: " + e.getMessage());
        }
    }

    // Listar todos os clientes
    public static void listarClientes() {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoDatabase database = mongoClient.getDatabase("PetShop");
            MongoCollection<Document> clientesCollection = database.getCollection("Cliente");

            System.out.println("Lista de Clientes:");
            for (Document doc : clientesCollection.find()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(doc.getInteger("idCliente"));
                cliente.setNome(doc.getString("nome"));
                cliente.setCpf(doc.getString("cpf"));
                cliente.setTelefone(doc.getString("telefone"));
                cliente.setEmail(doc.getString("email"));

                System.out.println("ID: " + cliente.getIdCliente() +
                        ", Nome: " + cliente.getNome() +
                        ", CPF: " + cliente.getCpf() +
                        ", Telefone: " + cliente.getTelefone() +
                        ", Email: " + cliente.getEmail());
            }
        }
    }

    // Atualizar as informações de um cliente
    public static void atualizarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do cliente a ser atualizado: ");
        long idCliente = Long.parseLong(scanner.nextLine());

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoDatabase database = mongoClient.getDatabase("PetShop");
            MongoCollection<Document> clientesCollection = database.getCollection("Cliente");

            Document clienteDoc = clientesCollection.find(new Document("idCliente", idCliente)).first();
            if (clienteDoc == null) {
                System.out.println("Erro: Cliente não encontrado.");
                return;
            }

            Cliente cliente = new Cliente();
            cliente.setIdCliente(clienteDoc.getInteger("idCliente"));
            cliente.setNome(clienteDoc.getString("nome"));
            cliente.setCpf(clienteDoc.getString("cpf"));
            cliente.setTelefone(clienteDoc.getString("telefone"));
            cliente.setEmail(clienteDoc.getString("email"));

            System.out.println("Atualizando informações do cliente: " + cliente.getNome());

            System.out.println("Novo nome (deixe em branco para manter o atual): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) cliente.setNome(nome);

            System.out.println("Novo CPF (deixe em branco para manter o atual): ");
            String cpf = scanner.nextLine();
            if (!cpf.isEmpty()) cliente.setCpf(cpf);

            System.out.println("Novo telefone (deixe em branco para manter o atual): ");
            String telefone = scanner.nextLine();
            if (!telefone.isEmpty()) cliente.setTelefone(telefone);

            System.out.println("Novo email (deixe em branco para manter o atual): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) cliente.setEmail(email);

            Document update = new Document("$set", new Document("nome", cliente.getNome())
                    .append("cpf", cliente.getCpf())
                    .append("telefone", cliente.getTelefone())
                    .append("email", cliente.getEmail()));

            clientesCollection.updateOne(new Document("idCliente", idCliente), update);
            System.out.println("Cliente atualizado com sucesso!");
        }
    }

    // Remover cliente
    public static void removerCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do cliente a ser removido: ");
        long idCliente = Long.parseLong(scanner.nextLine());

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> clientesCollection = mongoClient.getDatabase("PetShop").getCollection("Cliente");
            long deletedCount = clientesCollection.deleteOne(new Document("idCliente", idCliente)).getDeletedCount();

            if (deletedCount > 0) {
                System.out.println("Cliente removido com sucesso!");
            } else {
                System.out.println("Erro: Cliente não encontrado.");
            }
        }
    }
}
