public class Veiculo {
    private int id;
    private String modelo;
    private String marca;
    private String placa;
    private int ano;
    private double valorDiaria;
    private Categoria categoria;
    private int capacidadePassageiros;
    private double quilometragemAtual;
    private String status;

    public Veiculo(int id, String modelo, String marca,String placa, int ano, double valorDiaria,
                   Categoria categoria, int capacidadePassageiros, double quilometragemAtual, String status) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.placa = placa;
        this.ano = ano;
        this.valorDiaria = valorDiaria;
        this.categoria = categoria;
        this.capacidadePassageiros = capacidadePassageiros;
        this.quilometragemAtual = quilometragemAtual;
        this.status = status;
    }

    //Basicamente métodos set e get
    public int getId() {

        return id;
    }

    public String getModelo() {

        return modelo;
    }

    public String getMarca() {

        return marca;
    }

    public String getPlaca() {

        return placa;
    }

    public int getAno() {

        return ano;
    }

    public double getValorDiaria() {

        return valorDiaria;
    }

    public Categoria getCategoria() {

        return categoria;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setValorDiaria(double valorDiaria) {

        this.valorDiaria = valorDiaria;
    }

    public void setCategoria(Categoria categoria) {

        this.categoria = categoria;
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    public void setCapacidadeMaxima(int capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }
    public double getQuilometragem() {
        return quilometragemAtual;
    }
    public void setQuilometragemAtual(double quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        if (status.equals("DISPONIVEL") ||
                status.equals("RESERVADO") ||
                status.equals("ALUGADO") ||
                status.equals("MANUTENCAO")) {

            this.status = status;

        } else {
            System.out.println("Status inválido!");
        }
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nModelo: " + modelo +
                "\nMarca: " + marca +
                "\nPlaca: " + placa +
                "\nAno: " + ano +
                "\nDiária: R$ " + valorDiaria +
                "\nCategoria: " + categoria.getNome();
    }
}
