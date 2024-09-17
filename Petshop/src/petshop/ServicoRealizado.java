package petshop;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        String sql = "INSERT INTO ServicoRealizado (data, idDescricaoServico, idCliente, idPet, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(servico.getData().getTime()));
            stmt.setInt(2, servico.getIdDescricaoServico());
            stmt.setInt(3, servico.getIdCliente());
            stmt.setInt(4, servico.getIdPet());
            stmt.setString(5, servico.getStatus());
            stmt.executeUpdate();
            System.out.println("Serviço realizado cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar serviço: " + e.getMessage());
        }
    }

    public static void listarServicos() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sql = "SELECT sr.idServico, ds.servicoDescricao, p.nome AS petNome, sr.data, sr.status, c.idCliente " +
                "FROM ServicoRealizado sr " +
                "JOIN DescricaoServico ds ON sr.idDescricaoServico = ds.idDescricaoServico " +
                "JOIN Pet p ON sr.idPet = p.idPet " +
                "JOIN PetDono pd ON p.idPet = pd.idPet " +
                "JOIN Cliente c ON pd.idCliente = c.idCliente";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Lista de Serviços Realizados:");
            while (rs.next()) {
                int idServico = rs.getInt("idServico");
                String servicoDescricao = rs.getString("servicoDescricao");
                String petNome = rs.getString("petNome");
                Date data = rs.getDate("data");
                String status = rs.getString("status");
                int idCliente = rs.getInt("idCliente");

                System.out.println("ID: " + idServico +
                        ", Descrição: " + servicoDescricao +
                        ", Pet Nome: " + petNome +
                        ", Data: " + dateFormat.format(data) +
                        ", Status: " + status +
                        ", ID Cliente: " + idCliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar serviços: " + e.getMessage());
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

            String sql = "UPDATE ServicoRealizado SET data = ?, idDescricaoServico = ?, idCliente = ?, idPet = ?, status = ? WHERE idServico = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, new java.sql.Date(servico.getData().getTime()));
                stmt.setInt(2, servico.getIdDescricaoServico());
                stmt.setInt(3, servico.getIdCliente());
                stmt.setInt(4, servico.getIdPet());
                stmt.setString(5, servico.getStatus());
                stmt.setInt(6, servico.getIdServico());
                stmt.executeUpdate();
                System.out.println("Serviço atualizado com sucesso!");
            } catch (SQLException e) {
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
            String sql = "DELETE FROM ServicoRealizado WHERE idServico = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Serviço removido com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao remover serviço: " + e.getMessage());
            }
        } else {
            System.out.println("Serviço com ID " + id + " não encontrado.");
        }
    }

    public static ServicoRealizado buscarPorId(int idServico) {
        String sql = "SELECT * FROM ServicoRealizado WHERE idServico = ?";
        ServicoRealizado servico = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    servico = new ServicoRealizado();
                    servico.setIdServico(rs.getInt("idServico"));
                    servico.setData(rs.getDate("data"));
                    servico.setIdDescricaoServico(rs.getInt("idDescricaoServico"));
                    servico.setIdCliente(rs.getInt("idCliente"));
                    servico.setIdPet(rs.getInt("idPet"));
                    servico.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar serviço: " + e.getMessage());
        }

        return servico;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "ServicoRealizado{" + "idServico=" + idServico + ", data=" + dateFormat.format(data) +
                ", idDescricaoServico=" + idDescricaoServico + ", idCliente=" + idCliente +
                ", idPet=" + idPet + ", status='" + status + '\'' + '}';
    }
}
