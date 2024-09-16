package petshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import main.Conexao;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    // Construtores
    public Cliente() {
    }

    public Cliente(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

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

    // Método para adicionar um cliente
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

        Cliente cliente = new Cliente(nome, cpf, telefone, email);

        String sql = "INSERT INTO Cliente (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.executeUpdate();
            System.out.println("Cliente adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
        }
    }

    // Método para listar clientes
    public static void listarClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Lista de Clientes:");
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                System.out.println(cliente);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    // Método para atualizar um cliente
    public static void atualizarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do cliente a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir a quebra de linha

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

            String sql = "UPDATE Cliente SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE idCliente = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getCpf());
                stmt.setString(3, cliente.getTelefone());
                stmt.setString(4, cliente.getEmail());
                stmt.setInt(5, cliente.getIdCliente());
                stmt.executeUpdate();
                System.out.println("Cliente atualizado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            }
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
        }
    }

    // Método para remover um cliente
    public static void removerCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do cliente a ser removido: ");
        int id = scanner.nextInt();

        Cliente cliente = buscarPorId(id);
        if (cliente != null) {
            String sql = "DELETE FROM Cliente WHERE idCliente = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Cliente removido com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao remover cliente: " + e.getMessage());
            }
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
        }
    }

    // Método para buscar um cliente por ID
    public static Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM Cliente WHERE idCliente = ?";
        Cliente cliente = null;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    // Sobrescrevendo o método toString para facilitar a exibição dos dados
    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
