package br.edu.ifba.controleveiculos.model;

/**
 * Representa a pessoa que realiza uma reserva ou aluguel
 * (contribuição: entidade da locadora).
 */
public class Cliente {

    private final int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String cnh;

    public Cliente(int id, String nome, String cpf, String telefone, String email, String cnh) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.cnh = cnh;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome=" + nome + ", cpf=" + cpf + "}";
    }
}
