package petshop;

import main.Conexao;

public class DescricaoServico {
    private int idDescricaoServico;
    private String servicoDescricao;
    private double valor;

    public DescricaoServico(int idDescricaoServico, String servicoDescricao, double valor) {
        this.idDescricaoServico = idDescricaoServico;
        this.servicoDescricao = servicoDescricao;
        this.valor = valor;
    }

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

    // Implementar os métodos CRUD
}
