package br.edu.ifba.controleveiculos.model;

/**
 * Classifica os veículos de acordo com suas características
 * (contribuição de Mateus Oliveira de Araujo — US01/US02).
 */
public class Categoria {

    private final int id;
    private String nome;
    private String descricao;

    public Categoria(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Categoria: " + nome + " | Descrição: " + descricao;
    }
}
