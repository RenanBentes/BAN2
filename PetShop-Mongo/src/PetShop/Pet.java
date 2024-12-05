package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Scanner;
import main.Conexao;

public class Pet {
    private int idPet;
    private String nome;
    private String dataNascimento; // Formato dd/mm/aaaa
    private int idPetRaca;
    private int idCliente;

    // Construtor
    public Pet() {}

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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
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

    public static void adicionarPet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do pet: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a data de nascimento do pet (dd/MM/yyyy): ");
        String dataNascimento = scanner.nextLine();

        System.out.println("Digite o ID da raça do pet: ");
        int idPetRaca = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o ID do cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        try (MongoClient mongoClient = Conexao.getMongoClient()) {
            MongoDatabase database = Conexao.getDatabase();
            MongoCollection<Document> petsCollection = database.getCollection("Pet");
            MongoCollection<Document> clientesCollection = database.getCollection("Cliente");
            MongoCollection<Document> racasCollection = database.getCollection("Raca");

            // Validar se o cliente existe
            Document clienteDoc = clientesCollection.find(new Document("idCliente", idCliente)).first();
            if (clienteDoc == null) {
                System.out.println("Erro: Cliente com ID " + idCliente + " não encontrado. O pet não pode ser cadastrado.");
                return;
            }

            // Validar se a raça existe
            Document racaDoc = racasCollection.find(new Document("idPetRaca", idPetRaca)).first();
            if (racaDoc == null) {
                System.out.println("Erro: Raça com ID " + idPetRaca + " não encontrada. O pet não pode ser cadastrado.");
                return;
            }

            // Obter o maior ID existente para criar um novo
            Document ultimoPet = petsCollection.find()
                    .sort(new Document("idPet", -1))
                    .first();
            int ultimoIdPet = (ultimoPet != null) ? ultimoPet.getInteger("idPet", 0) : 0;
            int novoIdPet = ultimoIdPet + 1;

            // Criar o documento do pet
            Document petDoc = new Document("idPet", novoIdPet)
                    .append("nome", nome)
                    .append("datanascimento", dataNascimento)
                    .append("idPetRaca", idPetRaca)
                    .append("idCliente", idCliente);

            petsCollection.insertOne(petDoc);
            System.out.println("Pet adicionado com sucesso! ID: " + novoIdPet);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar o pet: " + e.getMessage());
        }
    }

    public static void listarPets() {
        try (MongoClient mongoClient = Conexao.getMongoClient()) {
            MongoCollection<Document> petsCollection = Conexao.getDatabase().getCollection("Pet");

            System.out.println("Lista de Pets:");
            for (Document doc : petsCollection.find()) {
                System.out.println("ID: " + doc.getInteger("idPet") +
                        ", Nome: " + doc.getString("nome") +
                        ", Data de Nascimento: " + doc.getString("datanascimento") +
                        ", ID Raça: " + doc.getInteger("idPetRaca") +
                        ", ID Dono: " + doc.getInteger("idCliente"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar os pets: " + e.getMessage());
        }
    }

    public static void atualizarPet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do pet a ser atualizado: ");
        int idPet = Integer.parseInt(scanner.nextLine());

        try (MongoClient mongoClient = Conexao.getMongoClient()) {
            MongoCollection<Document> petsCollection = Conexao.getDatabase().getCollection("Pet");

            // Buscar o pet pelo ID
            Document petDoc = petsCollection.find(new Document("idPet", idPet)).first();
            if (petDoc == null) {
                System.out.println("Erro: Pet não encontrado.");
                return;
            }

            System.out.println("Atualizando informações do pet: " + petDoc.getString("nome"));

            System.out.println("Novo nome (deixe em branco para manter o atual): ");
            String nome = scanner.nextLine();
            if (nome.isEmpty()) nome = petDoc.getString("nome");

            System.out.println("Nova data de nascimento (deixe em branco para manter a atual): ");
            String dataNascimento = scanner.nextLine();
            if (dataNascimento.isEmpty()) dataNascimento = petDoc.getString("datanascimento");

            System.out.println("Novo ID de raça (deixe em branco para manter o atual): ");
            String idPetRaca = scanner.nextLine();
            int novoIdPetRaca = idPetRaca.isEmpty() ? petDoc.getInteger("idPetRaca") : Integer.parseInt(idPetRaca);

            System.out.println("Novo ID do cliente (deixe em branco para manter o atual): ");
            String idCliente = scanner.nextLine();
            int novoIdCliente = idCliente.isEmpty() ? petDoc.getInteger("idCliente") : Integer.parseInt(idCliente);

            // Atualizar no banco
            Document update = new Document("$set", new Document("nome", nome)
                    .append("datanascimento", dataNascimento)
                    .append("idPetRaca", novoIdPetRaca)
                    .append("idCliente", novoIdCliente));

            petsCollection.updateOne(new Document("idPet", idPet), update);
            System.out.println("Pet atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o pet: " + e.getMessage());
        }
    }

    public static void removerPet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do pet a ser removido: ");
        int idPet = Integer.parseInt(scanner.nextLine());

        try (MongoClient mongoClient = Conexao.getMongoClient()) {
            MongoCollection<Document> petsCollection = Conexao.getDatabase().getCollection("Pet");

            long deletedCount = petsCollection.deleteOne(new Document("idPet", idPet)).getDeletedCount();

            if (deletedCount > 0) {
                System.out.println("Pet removido com sucesso!");
            } else {
                System.out.println("Erro: Pet não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao remover o pet: " + e.getMessage());
        }
    }
}