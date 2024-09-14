package petshop;

import main.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Raca {
    private int idPetRaca;
    private String descricao;

    public Raca(int idPetRaca, String descricao) {
        this.idPetRaca = idPetRaca;
        this.descricao = descricao;
    }

    public Raca() {}

    public int getIdPetRaca() {
        return idPetRaca;
    }

    public void setIdPetRaca(int idPetRaca) {
        this.idPetRaca = idPetRaca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void salvarRaca() {
        String sql = "INSERT INTO Raca (descricao) VALUES (?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.descricao);
            stmt.executeUpdate();
            System.out.println("Raça cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar raça: " + e.getMessage());
        }
    }

    public static ArrayList<Raca> listarRacas() {
        ArrayList<Raca> racas = new ArrayList<>();
        String sql = "SELECT * FROM Raca";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Raca raca = new Raca();
                raca.setIdPetRaca(rs.getInt("idPetRaca"));
                raca.setDescricao(rs.getString("descricao"));
                racas.add(raca);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar raças: " + e.getMessage());
        }

        return racas;
    }

    public void atualizarRaca() {
        String sql = "UPDATE Raca SET descricao = ? WHERE idPetRaca = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.descricao);
            stmt.setInt(2, this.idPetRaca);
            stmt.executeUpdate();
            System.out.println("Raça atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar raça: " + e.getMessage());
        }
    }

    public void deletarRaca() {
        String sql = "DELETE FROM Raca WHERE idPetRaca = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idPetRaca);
            stmt.executeUpdate();
            System.out.println("Raça deletada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar raça: " + e.getMessage());
        }
    }
}
