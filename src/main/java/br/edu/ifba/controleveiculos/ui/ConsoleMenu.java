package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Permissao;
import br.edu.ifba.controleveiculos.model.Usuario;

import java.util.Scanner;

/**
 * Menu principal do console com controle de permissões por perfil.
 * <p>
 * Cada item só é exibido para quem possui a permissão correspondente
 * (contribuição de Henrique Barbosa Ferrão — US09/US10, integrado ao restante).
 */
public class ConsoleMenu {

    private final Usuario logado;
    private final Scanner entrada;
    private final TelaDashboard telaDashboard;
    private final MenuUsuarios menuUsuarios;
    private final TelaVeiculo telaVeiculo;
    private final TelaDisponibilidade telaDisponibilidade;
    private final TelaReserva telaReserva;
    private final TelaAluguel telaAluguel;
    private final TelaManutencao telaManutencao;

    public ConsoleMenu(Usuario logado, Scanner entrada,
                       TelaDashboard telaDashboard, MenuUsuarios menuUsuarios,
                       TelaVeiculo telaVeiculo, TelaDisponibilidade telaDisponibilidade,
                       TelaReserva telaReserva, TelaAluguel telaAluguel,
                       TelaManutencao telaManutencao) {
        this.logado = logado;
        this.entrada = entrada;
        this.telaDashboard = telaDashboard;
        this.menuUsuarios = menuUsuarios;
        this.telaVeiculo = telaVeiculo;
        this.telaDisponibilidade = telaDisponibilidade;
        this.telaReserva = telaReserva;
        this.telaAluguel = telaAluguel;
        this.telaManutencao = telaManutencao;
    }

    public void exibir() {
        System.out.println("\n==========================================");
        System.out.println(" CONTROLE DE VEÍCULOS E DISPONIBILIDADE");
        System.out.println("==========================================");
        int opcao;
        do {
            System.out.println();
            System.out.println("Usuário: " + logado.getNome() + " (" + logado.getPerfil() + ")");
            System.out.println("--------- MENU PRINCIPAL ---------");
            if (tem(Permissao.VER_DASHBOARD)) {
                System.out.println("1 - Dashboard da frota");
            }
            if (tem(Permissao.GERENCIAR_USUARIOS)) {
                System.out.println("2 - Gerenciar usuários");
            }
            if (tem(Permissao.CONSULTAR_VEICULOS) || tem(Permissao.CADASTRAR_VEICULO)) {
                System.out.println("3 - Veículos e categorias");
            }
            if (tem(Permissao.VERIFICAR_DISPONIBILIDADE) || tem(Permissao.REALIZAR_RESERVA)) {
                System.out.println("4 - Disponibilidade e reservas");
            }
            if (tem(Permissao.REGISTRAR_RETIRADA) || tem(Permissao.REGISTRAR_DEVOLUCAO)) {
                System.out.println("5 - Aluguel e quilometragem");
            }
            if (tem(Permissao.CADASTRAR_MANUTENCAO) || tem(Permissao.VER_VEICULOS_PARA_MANUTENCAO)) {
                System.out.println("6 - Manutenção");
            }
            System.out.println("0 - Sair");
            opcao = Entrada.inteiro("Opção: ");
            switch (opcao) {
                case 1 -> { if (tem(Permissao.VER_DASHBOARD)) telaDashboard.exibir(logado); else invalida(); }
                case 2 -> { if (tem(Permissao.GERENCIAR_USUARIOS)) menuUsuarios.exibir(logado); else invalida(); }
                case 3 -> { if (tem(Permissao.CONSULTAR_VEICULOS) || tem(Permissao.CADASTRAR_VEICULO)) telaVeiculo.exibir(); else invalida(); }
                case 4 ->SubMenuDisponibilidadeReservas();
                case 5 -> { if (tem(Permissao.REGISTRAR_RETIRADA) || tem(Permissao.REGISTRAR_DEVOLUCAO)) telaAluguel.exibir(); else invalida(); }
                case 6 -> { if (tem(Permissao.CADASTRAR_MANUTENCAO) || tem(Permissao.VER_VEICULOS_PARA_MANUTENCAO)) telaManutencao.exibir(); else invalida(); }
                case 0 -> System.out.println("Encerrando a sessão...");
                default -> invalida();
            }
        } while (opcao != 0);
    }

    private void SubMenuDisponibilidadeReservas() {
        if (!tem(Permissao.VERIFICAR_DISPONIBILIDADE) && !tem(Permissao.REALIZAR_RESERVA)) {
            invalida();
            return;
        }
        int opcao;
        do {
            System.out.println();
            System.out.println("----- Disponibilidade e Reservas -----");
            if (tem(Permissao.VERIFICAR_DISPONIBILIDADE)) System.out.println("1 - Consultar disponibilidade");
            if (tem(Permissao.REALIZAR_RESERVA)) System.out.println("2 - Reservas");
            System.out.println("0 - Voltar");
            opcao = Entrada.inteiro("Opção: ");
            switch (opcao) {
                case 1 -> { if (tem(Permissao.VERIFICAR_DISPONIBILIDADE)) telaDisponibilidade.exibir(); else invalida(); }
                case 2 -> { if (tem(Permissao.REALIZAR_RESERVA)) telaReserva.exibir(); else invalida(); }
                case 0 -> { }
                default -> invalida();
            }
        } while (opcao != 0);
    }

    private boolean tem(Permissao permissao) {
        return logado.temPermissao(permissao);
    }

    private void invalida() {
        System.out.println("Acesso negado: sua permissão não permite esta operação.");
    }
}
