package petshop;

import java.util.Date;

public class PetDono {
    private int idPet;
    private int idCliente;
    private Date dataInicio;
    private Date dataFim;

    public PetDono(int idPet, int idCliente, Date dataInicio, Date dataFim) {
        this.idPet = idPet;
        this.idCliente = idCliente;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    // Implementar os métodos CRUD
}
