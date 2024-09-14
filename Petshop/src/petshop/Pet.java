package petshop;

import main.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Pet {
    private int idPet;
    private String nome;
    private int idade;
    private Date dataNascimento;
    private int idPetRaca;
    private int idCliente;

    public Pet() {}

    public Pet(int idPet, String nome, int idade, Date dataNascimento, int idPetRaca, int idCliente) {
        this.idPet = idPet;
        this.nome = nome;
        this.idade = idade;
        this.dataNascimento = dataNascimento;
        this.idPetRaca = idPetRaca;
        this.idCliente = idCliente;
    }

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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
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

    // Método para salvar um novo pet
    public void salvarPet() {
        String sql = "INSERT INTO Pet (nome, idade, dataNascimento, idPetRaca, idCliente) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.nome);
            stmt.setInt(2, this.idade);
            stmt.setDate(3, new java.sql.Date(this.dataNascimento.getTime()));
            stmt.setInt(4, this.idPetRaca);
            stmt.setInt(5, this.idCliente);
            stmt.executeUpdate();
            System.out.println("Pet cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar pet: " + e.getMessage());
        }
    }

    // Método para listar todos os pets
    public static ArrayList<Pet> listarPets() {
        ArrayList<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM Pet";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setIdPet(rs.getInt("idPet"));
                pet.setNome(rs.getString("nome"));
                pet.setIdade(rs.getInt("idade"));
                pet.setDataNascimento(rs.getDate("dataNascimento"));
                pet.setIdPetRaca(rs.getInt("idPetRaca"));
                pet.setIdCliente(rs.getInt("idCliente"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pets: " + e.getMessage());
        }

        return pets;
    }

    // Método para atualizar os dados de um pet
    public void atualizarPet() {
        String sql = "UPDATE Pet SET nome = ?, idade = ?, dataNascimento = ?, idPetRaca = ?, idCliente = ? WHERE idPet = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.nome);
            stmt.setInt(2, this.idade);
            stmt.setDate(3, new java.sql.Date(this.dataNascimento.getTime()));
            stmt.setInt(4, this.idPetRaca);
            stmt.setInt(5, this.idCliente);
            stmt.setInt(6, this.idPet);
            stmt.executeUpdate();
            System.out.println("Pet atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pet: " + e.getMessage());
        }
    }

    // Método para deletar um pet
    public void deletarPet() {
        String sql = "DELETE FROM Pet WHERE idPet = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idPet);
            stmt.executeUpdate();
            System.out.println("Pet deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar pet: " + e.getMessage());
        }
    }
}
