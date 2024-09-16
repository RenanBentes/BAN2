package petshop;

public class PetDono {
    private int idPet;
    private int idCliente;

    // Construtores
    public PetDono(int idPet, int idCliente) {
        this.idPet = idPet;
        this.idCliente = idCliente;
    }

    // Getters e Setters
    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // Sobrescrevendo o método toString para facilitar a exibição dos dados
    @Override
    public String toString() {
        return "PetDono{" +
                "idPet=" + idPet +
                ", idCliente=" + idCliente +
                '}';
    }
}
