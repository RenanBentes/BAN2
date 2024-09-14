package petshop;

import java.util.Date;

public class ServicoRealizado {
    private int idServico;
    private Date data;
    private int idDescricaoServico;
    private int idCliente;
    private int idPet;
    private String status;

    public ServicoRealizado(int idServico, Date data, int idDescricaoServico, int idCliente, int idPet, String status) {
        this.idServico = idServico;
        this.data = data;
        this.idDescricaoServico = idDescricaoServico;
        this.idCliente = idCliente;
        this.idPet = idPet;
        this.status = status;
    }

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

    // Implementar os métodos CRUD
}
