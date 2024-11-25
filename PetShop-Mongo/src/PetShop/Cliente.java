package PetShop;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import main.Conexao;

import java.util.Scanner;

public class Cliente {
    private int idCliente;
    private String nome;
    private long cpf; // CPF agora é long
    private String telefone;
    private String email;

    // Construtor padrão
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

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
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
    public static boolean existeCliente(long cpf) {
        try {
            MongoCollection<Document> clientes = Conexao.getDatabase().getCollection("Cliente");
            return clientes.countDocuments(new Document("cpf", cpf)) > 0;
        } catch (Exception e) {
            System.err.println("Erro ao verificar a existência do cliente: " + e.getMessage());
            return false;
        }
    }

    public static void adicionarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do cliente: ");
        String nome = scanner.nextLine().trim();

        System.out.println("Digite o CPF do cliente (apenas números): ");
        String cpfInput = scanner.nextLine().trim();

        if (cpfInput.length() != 11 || !cpfInput.matches("\\d+")) {
            System.out.println("Erro: CPF inválido. Certifique-se de que tenha 11 dígitos numéricos.");
            return;
        }

        long cpf = Long.parseLong(cpfInput);

        if (existeCliente(cpf)) {
            System.out.println("Erro: Cliente com CPF " + cpfInput + " já existe.");
            return;
        }

        System.out.println("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine().trim();

        System.out.println("Digite o email do cliente: ");
        String email = scanner.nextLine().trim();

        try {
            MongoCollection<Document> clientes = Conexao.getDatabase().getCollection("Cliente");

            Document ultimoCliente = clientes.find().sort(new Document("idCliente", -1)).first();
            int novoIdCliente = (ultimoCliente != null ? ultimoCliente.getInteger("idCliente", 0) : 0) + 1;

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

    public static void listarClientes() {
        try {
            MongoCollection<Document> clientes = Conexao.getDatabase().getCollection("Cliente");

            System.out.println("Lista de Clientes:");
            for (Document doc : clientes.find()) {
                System.out.println("ID: " + doc.getInteger("idCliente") +
                        ", Nome: " + doc.getString("nome") +
                        ", CPF: " + String.format("%011d", doc.getLong("cpf")) +
                        ", Telefone: " + doc.getString("telefone") +
                        ", Email: " + doc.getString("email"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar os clientes: " + e.getMessage());
        }
    }

    public static void atualizarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do cliente a ser atualizado: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        try {
            MongoCollection<Document> clientes = Conexao.getDatabase().getCollection("Cliente");

            Document clienteDoc = clientes.find(new Document("idCliente", idCliente)).first();
            if (clienteDoc == null) {
                System.out.println("Erro: Cliente não encontrado.");
                return;
            }

            System.out.println("Novo nome (deixe em branco para manter o atual): ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) nome = clienteDoc.getString("nome");

            System.out.println("Novo telefone (deixe em branco para manter o atual): ");
            String telefone = scanner.nextLine().trim();
            if (telefone.isEmpty()) telefone = clienteDoc.getString("telefone");

            System.out.println("Novo email (deixe em branco para manter o atual): ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) email = clienteDoc.getString("email");

            Document update = new Document("$set", new Document()
                    .append("nome", nome)
                    .append("telefone", telefone)
                    .append("email", email));

            clientes.updateOne(new Document("idCliente", idCliente), update);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o cliente: " + e.getMessage());
        }
    }

    public static void removerCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do cliente a ser removido: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        try {
            MongoCollection<Document> clientes = Conexao.getDatabase().getCollection("Cliente");

            if (clientes.deleteOne(new Document("idCliente", idCliente)).getDeletedCount() > 0) {
                System.out.println("Cliente removido com sucesso!");
            } else {
                System.out.println("Erro: Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao remover o cliente: " + e.getMessage());
        }
    }
}
