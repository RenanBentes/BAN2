package petshop;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import main.Conexao;

public class Pet {
    private int idPet;
    private String nome;
    private Date dataNascimento;
    private int idPetRaca;
    private int idCliente;

    // Construtores
    public Pet() {}

    public Pet(String nome, Date dataNascimento, int idPetRaca, int idCliente) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idPetRaca = idPetRaca;
        this.idCliente = idCliente;
    }

    // Getters e Setters
    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdPetRaca() {
        return idPetRaca;
    }

    public void setIdPetRaca(int idPetRaca) {
        this.idPetRaca = idPetRaca;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // Método para adicionar um pet
    public static void adicionarPet() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Digite o nome do pet: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a data de nascimento do pet (dd/mm/yyyy): ");
        String dataStr = scanner.nextLine();
        Date dataNascimento = null;
        try {
            dataNascimento = dateFormat.parse(dataStr);
        } catch (ParseException e) {
            System.out.println("Erro: Data em formato inválido.");
            return;
        }

        System.out.println("Digite o ID da raça do pet: ");
        int idPetRaca = scanner.nextInt();

        System.out.println("Digite o ID do cliente dono do pet: ");
        int idCliente = scanner.nextInt();

        Pet pet = new Pet(nome, dataNascimento, idPetRaca, idCliente);

        String sql = "INSERT INTO Pet (nome, dataNascimento, idPetRaca, idCliente) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pet.getNome());
            stmt.setDate(2, new java.sql.Date(pet.getDataNascimento().getTime()));
            stmt.setInt(3, pet.getIdPetRaca());
            stmt.setInt(4, pet.getIdCliente());
            stmt.executeUpdate();
            System.out.println("Pet adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar pet: " + e.getMessage());
        }
    }

    // Método para listar pets
    public static void listarPets() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM Pet";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Lista de Pets:");
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setIdPet(rs.getInt("idPet"));
                pet.setNome(rs.getString("nome"));
                pet.setDataNascimento(rs.getDate("dataNascimento"));
                pet.setIdPetRaca(rs.getInt("idPetRaca"));
                pet.setIdCliente(rs.getInt("idCliente"));
                System.out.println("ID: " + pet.getIdPet() +
                        ", Nome: " + pet.getNome() +
                        ", Data de Nascimento: " + dateFormat.format(pet.getDataNascimento()) +
                        ", ID Raça: " + pet.getIdPetRaca() +
                        ", ID Cliente: " + pet.getIdCliente());
                pets.add(pet);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pets: " + e.getMessage());
        }
    }

    // Método para atualizar um pet
    public static void atualizarPet() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Digite o ID do pet a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir quebra de linha

        Pet pet = buscarPorId(id);
        if (pet != null) {
            System.out.println("Atualizando informações do pet: " + pet.getNome());
            System.out.println("Novo nome (deixe em branco para manter o atual): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) {
                pet.setNome(nome);
            }
            System.out.println("Nova data de nascimento (deixe em branco para manter a atual, formato dd/mm/yyyy): ");
            String dataStr = scanner.nextLine();
            if (!dataStr.isEmpty()) {
                try {
                    pet.setDataNascimento(dateFormat.parse(dataStr));
                } catch (ParseException e) {
                    System.out.println("Erro: Data em formato inválido.");
                    return;
                }
            }
            System.out.println("Novo ID de raça (deixe em branco para manter o atual): ");
            String idPetRacaStr = scanner.nextLine();
            if (!idPetRacaStr.isEmpty()) {
                pet.setIdPetRaca(Integer.parseInt(idPetRacaStr));
            }
            System.out.println("Novo ID do cliente (deixe em branco para manter o atual): ");
            String idClienteStr = scanner.nextLine();
            if (!idClienteStr.isEmpty()) {
                pet.setIdCliente(Integer.parseInt(idClienteStr));
            }

            String sql = "UPDATE Pet SET nome = ?, dataNascimento = ?, idPetRaca = ?, idCliente = ? WHERE idPet = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pet.getNome());
                stmt.setDate(2, new java.sql.Date(pet.getDataNascimento().getTime()));
                stmt.setInt(3, pet.getIdPetRaca());
                stmt.setInt(4, pet.getIdCliente());
                stmt.setInt(5, pet.getIdPet());
                stmt.executeUpdate();
                System.out.println("Pet atualizado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar pet: " + e.getMessage());
            }
        } else {
            System.out.println("Pet com ID " + id + " não encontrado.");
        }
    }

    // Método para remover um pet
    public static void removerPet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do pet a ser removido: ");
        int id = scanner.nextInt();

        Pet pet = buscarPorId(id);
        if (pet != null) {
            String sql = "DELETE FROM Pet WHERE idPet = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Pet removido com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao remover pet: " + e.getMessage());
            }
        } else {
            System.out.println("Pet com ID " + id + " não encontrado.");
        }
    }

    // Método para buscar um pet por ID
    public static Pet buscarPorId(int idPet) {
        String sql = "SELECT * FROM Pet WHERE idPet = ?";
        Pet pet = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPet);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pet = new Pet();
                    pet.setIdPet(rs.getInt("idPet"));
                    pet.setNome(rs.getString("nome"));
                    pet.setDataNascimento(rs.getDate("dataNascimento"));
                    pet.setIdPetRaca(rs.getInt("idPetRaca"));
                    pet.setIdCliente(rs.getInt("idCliente"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pet: " + e.getMessage());
        }

        return pet;
    }

    // Sobrescrevendo o método toString para facilitar a exibição dos dados
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "Pet{" +
                "idPet=" + idPet +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dateFormat.format(dataNascimento) +
                ", idPetRaca=" + idPetRaca +
                ", idCliente=" + idCliente +
                '}';
    }
}
