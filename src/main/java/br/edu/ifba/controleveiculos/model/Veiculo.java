package br.edu.ifba.controleveiculos.model;

import java.util.Objects;

/**
 * Representa cada veículo pertencente à frota da locadora
 * (contribuição de Mateus Oliveira de Araujo — US01/US02).
 * <p>
 * O status segue o enum {@link StatusVeiculo} (RN05), consolidado à medida que
 * os módulos de aluguéis e manutenção também o utilizam.
 */
public class Veiculo {

    private final int id;
    private final String placa;
    private String marca;
    private String modelo;
    private int ano;
    private double valorDiaria;
    private Categoria categoria;
    private int capacidadePassageiros;
    private double quilometragemAtual;
    private StatusVeiculo status;

    public Veiculo(int id, String placa, String marca, String modelo, int ano,
                   double valorDiaria, Categoria categoria, int capacidadePassageiros,
                   double quilometragemAtual) {
        this.id = id;
        this.placa = Objects.requireNonNull(placa, "Placa é obrigatória.").toUpperCase();
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.valorDiaria = valorDiaria;
        this.categoria = categoria;
        this.capacidadePassageiros = capacidadePassageiros;
        this.quilometragemAtual = quilometragemAtual;
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public int getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    public void setCapacidadePassageiros(int capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }

    public double getQuilometragemAtual() {
        return quilometragemAtual;
    }

    public void setQuilometragemAtual(double quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = Objects.requireNonNull(status, "Status é obrigatório.");
    }

    public boolean estaDisponivel() {
        return status == StatusVeiculo.DISPONIVEL;
    }

    public boolean estaReservado() {
        return status == StatusVeiculo.RESERVADO;
    }

    public boolean estaAlugado() {
        return status == StatusVeiculo.ALUGADO;
    }

    public boolean estaEmManutencao() {
        return status == StatusVeiculo.MANUTENCAO;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nModelo: " + modelo +
                "\nMarca: " + marca +
                "\nPlaca: " + placa +
                "\nAno: " + ano +
                "\nDiária: R$ " + String.format("%.2f", valorDiaria) +
                "\nCategoria: " + categoria.getNome() +
                "\nCapacidade: " + capacidadePassageiros + " passageiros" +
                "\nQuilometragem: " + quilometragemAtual + " km" +
                "\nStatus: " + status;
    }
}
