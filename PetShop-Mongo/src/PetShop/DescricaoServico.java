package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import main.Conexao;
import java.util.ArrayList;
import java.util.Scanner;

public class DescricaoServico {
    private String idDescricaoServico; // Alterado para String, pois MongoDB usa ObjectId
    private String servicoDescricao;
    private double valor;

    public DescricaoServico() {}

    public DescricaoServico(String servicoDescricao, double valor) {
        this.servicoDescricao = servicoDescricao;
        this.valor = valor;
    }

    // Getters e Setters
    public String getIdDescricaoServico() {
        return idDescricaoServico;
    }

    public void setIdDescricaoServico(String idDescricaoServico) {
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

        DescricaoServico descricaoServico = new DescricaoServico(descricao, valor);

        Document descricaoDoc = new Document("servicoDescricao", descricaoServico.getServicoDescricao())
                .append("valor", descricaoServico.getValor());

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");
            descricaoServicoCollection.insertOne(descricaoDoc);
            System.out.println("Descrição de serviço adicionada com sucesso!");
        }
    }

    public static void listarDescricoesServico() {
        ArrayList<DescricaoServico> descricoes = new ArrayList<>();

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");
            MongoCursor<Document> cursor = descricaoServicoCollection.find().iterator();

            System.out.println("Lista de Descrições de Serviço:");
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                DescricaoServico descricaoServico = new DescricaoServico();
                descricaoServico.setIdDescricaoServico(doc.getObjectId("_id").toHexString());
                descricaoServico.setServicoDescricao(doc.getString("servicodescricao"));
                descricaoServico.setValor(doc.getDouble("valor"));
                System.out.println(descricaoServico);
                descricoes.add(descricaoServico);
            }
        }
    }

    public static void atualizarDescricaoServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da descrição de serviço a ser atualizada: ");
        String id = scanner.nextLine();

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

            try (MongoClient mongoClient = Conexao.getConexao()) {
                MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");
                Document query = new Document("_id", new ObjectId(id));
                Document update = new Document("$set", new Document("servicoDescricao", descricaoServico.getServicoDescricao())
                        .append("valor", descricaoServico.getValor()));
                descricaoServicoCollection.updateOne(query, update);
                System.out.println("Descrição de serviço atualizada com sucesso!");
            }
        } else {
            System.out.println("Descrição de serviço com ID " + id + " não encontrada.");
        }
    }

    public static void removerDescricaoServico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID da descrição de serviço a ser removida: ");
        String id = scanner.nextLine();

        DescricaoServico descricaoServico = buscarPorId(id);
        if (descricaoServico != null) {
            try (MongoClient mongoClient = Conexao.getConexao()) {
                MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");
                Document query = new Document("_id", new ObjectId(id));
                descricaoServicoCollection.deleteOne(query);
                System.out.println("Descrição de serviço removida com sucesso!");
            }
        } else {
            System.out.println("Descrição de serviço com ID " + id + " não encontrada.");
        }
    }

    public static DescricaoServico buscarPorId(String idDescricaoServico) {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> descricaoServicoCollection = mongoClient.getDatabase("PetShop").getCollection("DescricaoServico");
            Document query = new Document("_id", new ObjectId(idDescricaoServico));
            Document doc = descricaoServicoCollection.find(query).first();

            if (doc != null) {
                DescricaoServico descricaoServico = new DescricaoServico();
                descricaoServico.setIdDescricaoServico(doc.getObjectId("_id").toHexString());
                descricaoServico.setServicoDescricao(doc.getString("servicoDescricao"));
                descricaoServico.setValor(doc.getDouble("valor"));
                return descricaoServico;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DescricaoServico{" +
                "idDescricaoServico='" + idDescricaoServico + '\'' +
                ", servicoDescricao='" + servicoDescricao + '\'' +
                ", valor=" + valor +
                '}';
    }
}
