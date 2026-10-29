package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Aluguel;
import br.edu.ifba.controleveiculos.service.AluguelService;

import java.util.List;
import java.util.Scanner;

/**
 * Tela de aluguéis e quilometragem (US05/US06): retirada, devolução e histórico.
 */
public class TelaAluguel {

    private final Scanner entrada;
    private final AluguelService servico;
    private final TelaRetirada telaRetirada;
    private final TelaDevolucao telaDevolucao;

    public TelaAluguel(Scanner entrada, AluguelService servico) {
        this.entrada = entrada;
        this.servico = servico;
        this.telaRetirada = new TelaRetirada(servico);
        this.telaDevolucao = new TelaDevolucao(servico);
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== ALUGUEL E QUILOMETRAGEM =====");
            System.out.println("1 - Registrar retirada");
            System.out.println("2 - Registrar devolução");
            System.out.println("3 - Consultar histórico");
            System.out.println("0 - Voltar");
            opcao = Entrada.inteiro("Opção: ");
            switch (opcao) {
                case 1 -> telaRetirada.exibir();
                case 2 -> telaDevolucao.exibir();
                case 3 -> historico();
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void historico() {
        System.out.println();
        System.out.println("1 - Histórico completo");
        System.out.println("2 - Por placa");
        System.out.println("3 - Por cliente (CPF)");
        int sub = Entrada.inteiro("Opção: ");
        switch (sub) {
            case 1 -> listar(servico.consultarHistorico());
            case 2 -> {
                String placa = Entrada.texto("Placa: ").toUpperCase();
                listar(servico.consultarHistoricoPorPlaca(placa));
            }
            case 3 -> {
                String cpf = Entrada.texto("CPF: ");
                listar(servico.consultarHistoricoPorCliente(cpf));
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    private void listar(List<Aluguel> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum aluguel no histórico.");
            return;
        }
        lista.forEach(System.out::println);
    }
}
