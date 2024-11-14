package petshop;

import main.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Raca {
    private int idPetRaca;
    private String descricao;

    // Construtores
    public Raca() {}

    public Raca(String descricao) {
        this.descricao = descricao;
    }

    // Getters e Setters
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

    public static void adicionarRaca() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a descrição da raça: ");
        String descricao = scanner.nextLine();

        Raca raca = new Raca(descricao);
        String sql = "INSERT INTO Raca (descricao) VALUES (?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, raca.getDescricao());
            stmt.executeUpdate();
            System.out.println("Raça cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar raça: " + e.getMessage());
        }
    }

    public static void listarRacas() {
        ArrayList<Raca> racas = new ArrayList<>();
        String sql = "SELECT * FROM Raca";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Lista de Raças:");
            while (rs.next()) {
                Raca raca = new Raca();
                raca.setIdPetRaca(rs.getInt("idPetRaca"));
                raca.setDescricao(rs.getString("descricao"));
                System.out.println("ID: " + raca.getIdPetRaca() + ", Descrição: " + raca.getDescricao());
                racas.add(raca);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar raças: " + e.getMessage());
        }
    }

    public static void atualizarRaca() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID da raça que deseja atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir quebra de linha

        Raca raca = buscarPorId(id);
        if (raca != null) {
            System.out.println("Digite a nova descrição da raça: ");
            String novaDescricao = scanner.nextLine();
            raca.setDescricao(novaDescricao);

            String sql = "UPDATE Raca SET descricao = ? WHERE idPetRaca = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, raca.getDescricao());
                stmt.setInt(2, raca.getIdPetRaca());
                stmt.executeUpdate();
                System.out.println("Raça atualizada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar raça: " + e.getMessage());
            }
        } else {
            System.out.println("Raça com ID " + id + " não encontrada.");
        }
    }

    public static void removerRaca() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da raça que deseja remover: ");
        int id = scanner.nextInt();

        Raca raca = buscarPorId(id);
        if (raca != null) {
            String sql = "DELETE FROM Raca WHERE idPetRaca = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Raça removida com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao remover raça: " + e.getMessage());
            }
        } else {
            System.out.println("Raça com ID " + id + " não encontrada.");
        }
    }

    public static Raca buscarPorId(int idPetRaca) {
        String sql = "SELECT * FROM Raca WHERE idPetRaca = ?";
        Raca raca = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPetRaca);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    raca = new Raca();
                    raca.setIdPetRaca(rs.getInt("idPetRaca"));
                    raca.setDescricao(rs.getString("descricao"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar raça: " + e.getMessage());
        }

        return raca;
    }
}
