package entities;

import java.time.LocalDate;

public class Estoque {

    private String nomeProduto;
    private LocalDate dataValidade;
    private LocalDate dataRecebimento;
    private int quantidade;


    public Estoque(String nomeProduto, LocalDate dataValidade, LocalDate dataRecebimento, int quantidade) {
        this.nomeProduto = nomeProduto;
        this.dataValidade = dataValidade;
        this.dataRecebimento = dataRecebimento;
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
