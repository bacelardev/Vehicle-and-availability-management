package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Categoria;
import br.edu.ifba.controleveiculos.model.Veiculo;
import br.edu.ifba.controleveiculos.service.CategoriaService;
import br.edu.ifba.controleveiculos.service.RegraNegocioException;
import br.edu.ifba.controleveiculos.service.VeiculoService;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.util.Comparator;
import java.util.Scanner;

/**
 * Tela de cadastro, consulta e organização de veículos (US01/US02).
 */
public class TelaVeiculo {

    private final Scanner entrada;
    private final VeiculoService veiculos;
    private final CategoriaService categorias;

    public TelaVeiculo(Scanner entrada, VeiculoService veiculos, CategoriaService categorias) {
        this.entrada = entrada;
        this.veiculos = veiculos;
        this.categorias = categorias;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== VEÍCULOS =====");
            System.out.println("1 - Cadastrar veículo");
            System.out.println("2 - Listar veículos");
            System.out.println("3 - Buscar por placa");
            System.out.println("4 - Consultar por categoria");
            System.out.println("5 - Ordenar veículos");
            System.out.println("6 - Remover veículo");
            System.out.println("0 - Voltar");
            opcao = Entrada.inteiro("Opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrar();
                    case 2 -> listar();
                    case 3 -> buscar();
                    case 4 -> porCategoria();
                    case 5 -> ordenar();
                    case 6 -> remover();
                    case 0 -> { }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RegraNegocioException | IllegalArgumentException e) {
                System.out.println("Não foi possível: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println();
        String placa = Entrada.texto("Placa: ").toUpperCase();
        String marca = Entrada.texto("Marca: ");
        String modelo = Entrada.texto("Modelo: ");
        int ano = Entrada.inteiro("Ano: ");
        double diaria = Entrada.decimal("Valor da diária: ");
        int capacidade = Entrada.inteiro("Capacidade de passageiros: ");
        double km = Entrada.decimal("Quilometragem atual: ");
        Categoria cat = selecionarCategoria();
        if (cat == null) {
            System.out.println("Nenhuma categoria disponível. Cadastre uma categoria primeiro.");
            return;
        }
        Veiculo v = veiculos.cadastrar(placa, marca, modelo, ano, diaria, cat, capacidade, km);
        System.out.println("Veículo cadastrado: " + v.getPlaca() + " (ID " + v.getId() + ").");
    }

    private Categoria selecionarCategoria() {
        if (!categorias.existeAlguma()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return null;
        }
        System.out.println("Categorias:");
        categorias.listar();
        int id = Entrada.inteiro("ID da categoria: ");
        Categoria c = categorias.buscarPorId(id);
        if (c == null) throw new IllegalArgumentException("Categoria não encontrada: " + id);
        return c;
    }

    private void listar() {
        Lista<Veiculo> lista = veiculos.listar();
        if (lista.vazia()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }
        lista.forEach(v -> { System.out.println(v); System.out.println("--------------------"); });
    }

    private void buscar() {
        String placa = Entrada.texto("Placa: ").toUpperCase();
        Veiculo v = veiculos.buscarPorPlaca(placa);
        System.out.println(v != null ? v : "Veículo não encontrado.");
    }

    private void porCategoria() {
        String nome = Entrada.texto("Nome da categoria: ");
        Lista<Veiculo> lista = veiculos.consultarPorCategoria(nome);
        if (lista.vazia()) {
            System.out.println("Nenhum veículo nessa categoria.");
            return;
        }
        lista.forEach(v -> System.out.println(v.getPlaca() + " - " + v.getModelo()));
    }

    private void ordenar() {
        System.out.println("Ordenar por: 1 - Categoria | 2 - Valor da diária | 3 - Ano");
        int crit = Entrada.inteiro("Critério: ");
        Comparator<Veiculo> c = switch (crit) {
            case 1 -> Comparator.comparing(v -> v.getCategoria().getNome().toLowerCase());
            case 2 -> Comparator.comparingDouble(Veiculo::getValorDiaria);
            case 3 -> Comparator.comparingInt(Veiculo::getAno);
            default -> throw new IllegalArgumentException("Critério inválido.");
        };
        veiculos.ordenar(c);
        System.out.println("Frota reordenada. Veículos por ordem:");
        veiculos.listar().forEach(v -> System.out.println(v.getPlaca() + " - " + v.getModelo()));
    }

    private void remover() {
        String placa = Entrada.texto("Placa: ").toUpperCase();
        if (veiculos.removerPorPlaca(placa)) {
            System.out.println("Veículo removido.");
        }
    }
}
