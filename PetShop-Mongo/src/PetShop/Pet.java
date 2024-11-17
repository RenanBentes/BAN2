package PetShop;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import main.Conexao;

public class Pet {
    private String idPet; // Alterado para String, pois MongoDB usa ObjectId
    private String nome;
    private Date dataNascimento;
    private int idPetRaca;

    // Construtores
    public Pet() {}

    public Pet(String nome, Date dataNascimento, int idPetRaca) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idPetRaca = idPetRaca;
    }

    // Getters e Setters
    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
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

    // Adiciona um novo pet ao banco de dados
    public static void adicionarPet() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Digite o nome do pet: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a data de nascimento do pet (dd/MM/yyyy): ");
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

        Pet pet = new Pet(nome, dataNascimento, idPetRaca);

        Document petDoc = new Document("nome", pet.getNome())
                .append("dataNascimento", pet.getDataNascimento())
                .append("idPetRaca", pet.getIdPetRaca());

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> petsCollection = mongoClient.getDatabase("PetShop").getCollection("Pet");
            petsCollection.insertOne(petDoc);
            System.out.println("Pet adicionado com sucesso!");
        }
    }

    public static void listarPets() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Pet> pets = new ArrayList<>();

        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> petsCollection = mongoClient.getDatabase("PetShop").getCollection("Pet");
            MongoCursor<Document> cursor = petsCollection.find().iterator();

            System.out.println("Lista de Pets:");
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Pet pet = new Pet();
                pet.setIdPet(doc.getObjectId("_id").toHexString());
                pet.setNome(doc.getString("nome"));
                pet.setDataNascimento(doc.getDate("dataNascimento"));
                pet.setIdPetRaca(doc.getInteger("idPetRaca"));
                System.out.println("ID: " + pet.getIdPet() +
                        ", Nome: " + pet.getNome() +
                        ", Data de Nascimento: " + dateFormat.format(pet.getDataNascimento()) +
                        ", ID Raça: " + pet.getIdPetRaca());
                pets.add(pet);
            }
        }
    }

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
            if (!nome.isEmpty()) {
                pet.setNome(nome);
            }
            System.out.println("Nova data de nascimento (deixe em branco para manter a atual, formato dd/MM/yyyy): ");
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

            try (MongoClient mongoClient = Conexao.getConexao()) {
                MongoCollection<Document> petsCollection = mongoClient.getDatabase("PetShop").getCollection("Pet");
                Document query = new Document("_id", new ObjectId(id));
                Document update = new Document("$set", new Document("nome", pet.getNome())
                        .append("dataNascimento", pet.getDataNascimento())
                        .append("idPetRaca", pet.getIdPetRaca()));
                petsCollection.updateOne(query, update);
                System.out.println("Pet atualizado com sucesso!");
            }
        } else {
            System.out.println("Pet com ID " + id + " não encontrado.");
        }
    }

    public static void removerPet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do pet a ser removido: ");
        String id = scanner.nextLine();

        Pet pet = buscarPorId(id);
        if (pet != null) {
            try (MongoClient mongoClient = Conexao.getConexao()) {
                MongoCollection<Document> petsCollection = mongoClient.getDatabase("PetShop").getCollection("Pet");
                Document query = new Document("_id", new ObjectId(id));
                petsCollection.deleteOne(query);
                System.out.println("Pet removido com sucesso!");
            }
        } else {
            System.out.println("Pet com ID " + id + " não encontrado.");
        }
    }

    public static Pet buscarPorId(String idPet) {
        try (MongoClient mongoClient = Conexao.getConexao()) {
            MongoCollection<Document> petsCollection = mongoClient.getDatabase("PetShop").getCollection("Pet");
            Document query = new Document("_id", new ObjectId(idPet));
            Document doc = petsCollection.find(query).first();

            if (doc != null) {
                Pet pet = new Pet();
                pet.setIdPet(doc.getObjectId("_id").toHexString());
                pet.setNome(doc.getString("nome"));
                pet.setDataNascimento(doc.getDate("datanascimento"));
                pet.setIdPetRaca(doc.getInteger("idpetraca"));
                return pet;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "Pet{" +
                "idPet='" + idPet + '\'' +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dateFormat.format(dataNascimento) +
                ", idPetRaca=" + idPetRaca +
                '}';
    }
}
