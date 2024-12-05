package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import main.Conexao;
import java.util.Scanner;

public class DescricaoServico {
    private int idDescricaoServico; // ID como inteiro
    private String servicoDescricao;
    private double valor;
    private int idPetRaca; // Adicionado idPetRaca

    // Construtor
    public DescricaoServico(int idDescricaoServico, String servicoDescricao, double valor, int idPetRaca) {
        this.idDescricaoServico = idDescricaoServico;
        this.servicoDescricao = servicoDescricao;
        this.valor = valor;
        this.idPetRaca = idPetRaca;
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

    public int getIdPetRaca() {
        return idPetRaca;
    }

    public void setIdPetRaca(int idPetRaca) {
        this.idPetRaca = idPetRaca;
    }

    @Override
    public String toString() {
        return "ID: " + idDescricaoServico + ", Descrição: " + servicoDescricao + ", Valor: R$ " + valor + ", ID da Raça: " + idPetRaca;
    }

    public static void adicionarDescricaoServico() {
        Scanner scanner = new Scanner(System.in);

        // Conexão com o MongoDB
        MongoClient mongoClient = Conexao.getMongoClient();
        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> descricaoServicoCollection = database.getCollection("DescricaoServico");

        // Solicitar os dados ao usuário
        System.out.println("Digite a descrição do serviço: ");
        String descricao = scanner.nextLine();

        System.out.println("Digite o valor do serviço: ");
        double valor = scanner.nextDouble();

        // Validar o ID da raça como inteiro
        int idPetRaca = -1;
        while (idPetRaca == -1) {
            System.out.println("Digite o ID da raça: ");
            idPetRaca = scanner.nextInt();

            // Verificar se o ID da raça existe na coleção
            MongoCollection<Document> racaCollection = database.getCollection("Raca");

            // Consultar pela raça usando o ID como inteiro
            Document racaDoc = racaCollection.find(new Document("idPetRaca", idPetRaca)).first();

            if (racaDoc != null) {
                // Raça encontrada, não imprimir nada
                break; // ID válido, continuar
            } else {
                System.out.println("Erro: Raça com ID " + idPetRaca + " não encontrada. Tente novamente.");
                idPetRaca = -1; // Continuar solicitando o ID até encontrar
            }
        }

        // Gerar um novo ID incremental para DescricaoServico
        int novoIdDescricaoServico;
        try {
            // Buscar o último ID inserido na coleção
            Document ultimoServico = descricaoServicoCollection.find()
                    .sort(new Document("idDescricaoServico", -1)) // Buscar o maior ID existente
                    .first();

            // Calcular o novo ID incremental
            novoIdDescricaoServico = (ultimoServico != null) ? ultimoServico.getInteger("idDescricaoServico") + 1 : 1;

            // Criar o documento da descrição do serviço
            Document descricaoDoc = new Document("idDescricaoServico", novoIdDescricaoServico)
                    .append("servicoDescricao", descricao)
                    .append("valor", valor)
                    .append("idPetRaca", idPetRaca); // Armazenar o ID da raça como inteiro

            // Inserir o novo documento no banco de dados
            descricaoServicoCollection.insertOne(descricaoDoc);
            System.out.println("Descrição de serviço adicionada com sucesso!"); // Não imprimir o documento da raça
        } catch (Exception e) {
            System.out.println("Erro ao adicionar descrição de serviço: " + e.getMessage());
        } finally {
            mongoClient.close(); // Fechar a conexão ao final do método
        }
    }



    public static void listarDescricoesServico() {
        try (MongoClient mongoClient = Conexao.getMongoClient()) {
            MongoCollection<Document> descricaoServicoCollection = Conexao.getDatabase().getCollection("DescricaoServico");

            System.out.println("Lista de Descrições de Serviço:");
            for (Document doc : descricaoServicoCollection.find()) {
                int idDescricaoServico = doc.getInteger("idDescricaoServico", 0);
                String servicoDescricao = doc.getString("servicoDescricao");
                double valor = doc.getDouble("valor") != null ? doc.getDouble("valor") : 0.0;
                int idPetRaca = doc.getInteger("idPetRaca", 0);

                DescricaoServico descricaoServico = new DescricaoServico(idDescricaoServico, servicoDescricao, valor, idPetRaca);
                System.out.println(descricaoServico);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar descrições de serviço: " + e.getMessage());
        }
    }

    public static void atualizarDescricaoServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da descrição de serviço a ser atualizada: ");
        int idDescricaoServico = scanner.nextInt();
        scanner.nextLine(); // Consumir o \n após o nextInt()

        try (MongoClient mongoClient = Conexao.getMongoClient()) {
            MongoCollection<Document> descricaoServicoCollection = Conexao.getDatabase().getCollection("DescricaoServico");

            Document query = new Document("idDescricaoServico", idDescricaoServico);
            Document doc = descricaoServicoCollection.find(query).first();

            if (doc != null) {
                System.out.println("Digite a nova descrição (deixe em branco para manter): ");
                String descricao = scanner.nextLine();
                if (descricao.isEmpty()) {
                    descricao = doc.getString("servicoDescricao");
                }

                System.out.println("Digite o novo valor (deixe em branco para manter): ");
                String valorStr = scanner.nextLine();
                double valor = valorStr.isEmpty() ? doc.getDouble("valor") : Double.parseDouble(valorStr);

                System.out.println("Digite o novo ID da raça (deixe em branco para manter): ");
                String idPetRacaStr = scanner.nextLine();
                int idPetRaca = idPetRacaStr.isEmpty() ? doc.getInteger("idPetRaca", 0) : Integer.parseInt(idPetRacaStr);

                Document update = new Document("$set", new Document("servicoDescricao", descricao)
                        .append("valor", valor)
                        .append("idPetRaca", idPetRaca));
                descricaoServicoCollection.updateOne(query, update);
                System.out.println("Descrição de serviço atualizada com sucesso!");
            } else {
                System.out.println("Descrição de serviço com ID " + idDescricaoServico + " não encontrada.");
            }
        }
    }

    public static void removerDescricaoServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da descrição de serviço a ser removida: ");
        int idDescricaoServico = scanner.nextInt();

        try (MongoClient mongoClient = Conexao.getMongoClient()) {
            MongoCollection<Document> descricaoServicoCollection = Conexao.getDatabase().getCollection("DescricaoServico");

            Document query = new Document("idDescricaoServico", idDescricaoServico);
            descricaoServicoCollection.deleteOne(query);
            System.out.println("Descrição de serviço removida com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao remover descrição de serviço: " + e.getMessage());
        }
    }
}