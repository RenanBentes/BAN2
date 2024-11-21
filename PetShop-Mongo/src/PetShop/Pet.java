package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import main.Conexao;

public class Pet {
    private int idPet;
    private String nome;
    private String dataNascimento; // Formato dd/MM/yyyy
    private int idPetRaca;
    private int idCliente;

    // Construtores
    public Pet() {}

    public Pet(String nome, String dataNascimento, int idPetRaca, int idCliente) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idPetRaca = idPetRaca;
        this.idCliente = idCliente;
    }

    // Getters e Setters
    public int getIdPet() { return idPet; }
    public void setIdPet(int idPet) { this.idPet = idPet; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public int getIdPetRaca() { return idPetRaca; }
    public void setIdPetRaca(int idPetRaca) { this.idPetRaca = idPetRaca; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    // Adicionar pet com validação do cliente
    public static void adicionarPet() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Digite o nome do pet: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a data de nascimento do pet (dd/MM/yyyy): ");
        String dataNascimento = scanner.nextLine();

        System.out.println("Digite o ID da raça do pet: ");
        int idPetRaca = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        System.out.println("Digite o ID do cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        // Verificar se o cliente existe
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoDatabase database = mongoClient.getDatabase("PetShop");

            MongoCollection<Document> clientesCollection = database.getCollection("Cliente");
            if (clientesCollection.find(new Document("idCliente", idCliente)).first() == null) {
                System.out.println("Erro: Cliente não encontrado.");
                return;
            }

            Pet pet = new Pet(nome, dataNascimento, idPetRaca, idCliente);
            Document petDoc = new Document("nome", pet.getNome())
                    .append("datanascimento", pet.getDataNascimento())
                    .append("idPetRaca", pet.getIdPetRaca())
                    .append("idCliente", pet.getIdCliente());

            MongoCollection<Document> petsCollection = database.getCollection("Pet");
            petsCollection.insertOne(petDoc);
            System.out.println("Pet adicionado com sucesso!");
        }
    }


    public static void listarPets() {
        MongoDatabase database = Conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("Pet");
        System.out.println("Lista de Pets:");

        for (Document doc : collection.find()) {
            Pet pet = new Pet();
            pet.setIdPet(doc.getInteger("idPet"));
            pet.setNome(doc.getString("nome"));
            pet.setDataNascimento(doc.getString("datanascimento"));
            pet.setIdPetRaca(doc.getInteger("idPetRaca"));
            pet.setIdCliente(doc.getInteger("idCliente"));

            System.out.println("ID: " + pet.getIdPet() +
                    ", Nome: " + pet.getNome() +
                    ", Data de Nascimento: " + pet.getDataNascimento() +
                    ", ID Raça: " + pet.getIdPetRaca() +
                    ", ID Dono: " + pet.getIdCliente());
        }
    }


    // Atualizar pet
    public static void atualizarPet() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Digite o ID do pet a ser atualizado: ");
        String id = scanner.nextLine();

        Pet pet = buscarPorId(id);
        if (pet != null) {
            System.out.println("Atualizando informações do pet: " + pet.getNome());

            System.out.println("Novo nome (deixe em branco para manter o atual): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) pet.setNome(nome);

            System.out.println("Nova data de nascimento (deixe em branco para manter a atual): ");
            String dataStr = scanner.nextLine();
            if (!dataStr.isEmpty()) {
                try {
                    dateFormat.parse(dataStr);
                    pet.setDataNascimento(dataStr);
                } catch (ParseException e) {
                    System.out.println("Erro: Data em formato inválido.");
                    return;
                }
            }

            System.out.println("Novo ID de raça (deixe em branco para manter o atual): ");
            String idPetRacaStr = scanner.nextLine();
            if (!idPetRacaStr.isEmpty()) pet.setIdPetRaca(Integer.parseInt(idPetRacaStr));

            System.out.println("Novo ID do cliente (deixe em branco para manter o atual): ");
            String idCliente = scanner.nextLine();
            if (!idCliente.isEmpty()) pet.setIdCliente(Integer.parseInt(idCliente));

            try (MongoClient mongoClient = Conexao.getConexao()) {
                MongoCollection<Document> petsCollection = mongoClient.getDatabase("PetShop").getCollection("Pet");
                Document query = new Document("_id", pet.getIdPet());
                Document update = new Document("$set", new Document("nome", pet.getNome())
                        .append("datanascimento", pet.getDataNascimento())
                        .append("idPetRaca", pet.getIdPetRaca())
                        .append("idCliente", pet.getIdCliente()));

                petsCollection.updateOne(query, update);
                System.out.println("Pet atualizado com sucesso!");
            }
        } else {
            System.out.println("Pet não encontrado.");
        }
    }

    public static void removerPet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do pet a ser removido: ");
        String id = scanner.nextLine();

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> petsCollection = mongoClient.getDatabase("PetShop").getCollection("Pet");
            long deletedCount = petsCollection.deleteOne(new Document("_id", id)).getDeletedCount();

            if (deletedCount > 0) {
                System.out.println("Pet removido com sucesso!");
            } else {
                System.out.println("Pet não encontrado.");
            }
        }
    }

    // Buscar pet por ID
    public static Pet buscarPorId(String idPet) {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> petsCollection = mongoClient.getDatabase("PetShop").getCollection("Pet");
            Document doc = petsCollection.find(new Document("idPet", idPet)).first();

            if (doc != null) {
                Pet pet = new Pet();
                pet.setIdPet(doc.getInteger("idPet"));
                pet.setNome(doc.getString("nome"));
                pet.setDataNascimento(doc.getString("datanascimento"));
                pet.setIdPetRaca(doc.getInteger("idPetRaca"));
                pet.setIdCliente(doc.getInteger("idCliente"));
                return pet;
            }
        }
        return null;
    }
}
