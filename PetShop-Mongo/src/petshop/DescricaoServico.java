package petshop;

import main.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DescricaoServico {
    private int idDescricaoServico;
    private String servicoDescricao;
    private double valor;

    // Construtores
    public DescricaoServico() {}

    public DescricaoServico(int idDescricaoServico, String servicoDescricao, double valor) {
        this.idDescricaoServico = idDescricaoServico;
        this.servicoDescricao = servicoDescricao;
        this.valor = valor;
    }

    // Getters e Setters
    public int getIdDescricaoServico() {
        return idDescricaoServico;
    }

    public void setIdDescricaoServico(int idDescricaoServico) {
        this.idDescricaoServico = idDescricaoServico;
    }

    public String getServicoDescricao() {
        return servicoDescricao;
    }

    public void setServicoDescricao(String servicoDescricao) {
        this.servicoDescricao = servicoDescricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public static void adicionarDescricaoServico() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a descrição do serviço: ");
        String descricao = scanner.nextLine();

        System.out.println("Digite o valor do serviço: ");
        double valor = scanner.nextDouble();

        DescricaoServico descricaoServico = new DescricaoServico(0, descricao, valor);

        String sql = "INSERT INTO DescricaoServico (servicoDescricao, valor) VALUES (?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricaoServico.getServicoDescricao());
            stmt.setDouble(2, descricaoServico.getValor());
            stmt.executeUpdate();
            System.out.println("Descrição de serviço adicionada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar descrição de serviço: " + e.getMessage());
        }
    }

    public static void listarDescricoesServico() {
        ArrayList<DescricaoServico> descricoes = new ArrayList<>();
        String sql = "SELECT * FROM DescricaoServico";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Lista de Descrições de Serviço:");
            while (rs.next()) {
                DescricaoServico descricaoServico = new DescricaoServico();
                descricaoServico.setIdDescricaoServico(rs.getInt("idDescricaoServico"));
                descricaoServico.setServicoDescricao(rs.getString("servicoDescricao"));
                descricaoServico.setValor(rs.getDouble("valor"));
                System.out.println(descricaoServico);
                descricoes.add(descricaoServico);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar descrições de serviço: " + e.getMessage());
        }
    }

    public static void atualizarDescricaoServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da descrição de serviço a ser atualizada: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        DescricaoServico descricaoServico = buscarPorId(id);
        if (descricaoServico != null) {
            System.out.println("Atualizando informações da descrição de serviço: ");
            System.out.println("Nova descrição (deixe em branco para manter a atual): ");
            String descricao = scanner.nextLine();
            if (!descricao.isEmpty()) {
                descricaoServico.setServicoDescricao(descricao);
            }
            System.out.println("Novo valor (deixe em branco para manter o atual): ");
            String valorStr = scanner.nextLine();
            if (!valorStr.isEmpty()) {
                descricaoServico.setValor(Double.parseDouble(valorStr));
            }

            String sql = "UPDATE DescricaoServico SET servicoDescricao = ?, valor = ? WHERE idDescricaoServico = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, descricaoServico.getServicoDescricao());
                stmt.setDouble(2, descricaoServico.getValor());
                stmt.setInt(3, descricaoServico.getIdDescricaoServico());
                stmt.executeUpdate();
                System.out.println("Descrição de serviço atualizada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar descrição de serviço: " + e.getMessage());
            }
        } else {
            System.out.println("Descrição de serviço com ID " + id + " não encontrada.");
        }
    }

    public static void removerDescricaoServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da descrição de serviço a ser removida: ");
        int id = scanner.nextInt();

        DescricaoServico descricaoServico = buscarPorId(id);
        if (descricaoServico != null) {
            String sql = "DELETE FROM DescricaoServico WHERE idDescricaoServico = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Descrição de serviço removida com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao remover descrição de serviço: " + e.getMessage());
            }
        } else {
            System.out.println("Descrição de serviço com ID " + id + " não encontrada.");
        }
    }

    public static DescricaoServico buscarPorId(int idDescricaoServico) {
        String sql = "SELECT * FROM DescricaoServico WHERE idDescricaoServico = ?";
        DescricaoServico descricaoServico = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDescricaoServico);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    descricaoServico = new DescricaoServico();
                    descricaoServico.setIdDescricaoServico(rs.getInt("idDescricaoServico"));
                    descricaoServico.setServicoDescricao(rs.getString("servicoDescricao"));
                    descricaoServico.setValor(rs.getDouble("valor"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar descrição de serviço: " + e.getMessage());
        }

        return descricaoServico;
    }

    @Override
    public String toString() {
        return "DescricaoServico{" +
                "idDescricaoServico=" + idDescricaoServico +
                ", servicoDescricao='" + servicoDescricao + '\'' +
                ", valor=" + valor +
                '}';
    }
}
